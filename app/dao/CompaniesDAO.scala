package dao

import javax.inject.{Singleton, Inject}
import scala.concurrent.Future
import models.Company
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

trait CompaniesComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class Companies(tag: Tag) extends Table[Company](tag, "COMPANY") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def * = (id.?, name) <> (Company.tupled, Company.unapply _)
  }

}

@Singleton()
class CompaniesDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends CompaniesComponent
  with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  val companies = TableQuery[Companies]
  //val insertAndReturnPKey = companies returning companies.map(_.id)
  //def autoInc = userId ~ name ~ refName returning id 
  /** Construct the Map[String,String] needed to fill a select options set */
  def options(): Future[Seq[(String, String)]] = {
    val query = (for {
      company <- companies
    } yield (company.id, company.name)).sortBy(/*name*/_._2)

    db.run(query.result).map(rows => rows.map { case (id, name) => (id.toString, name) })
  }

  /** Insert a new company */
  def insert(company: Company): Future[Unit] =
    //autoInc.insert(role.userId, role.name, role.refName) 
    db.run(companies += company).map(_ => ())

  /** Insert new companies */
  def insert(companies: Seq[Company]): Future[Unit] ={
    println("insert member Seqcompanies")
    println(companies.mkString(" "))
    db.run(this.companies ++= companies).map(_ => ())
  }

  def singleInsert(company: Company) = {
    println("single insert companies - no future")
    println(company.id)
    println(company.name)
    db.run(companies += company)
  }
}