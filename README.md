Working computer-database sample taken from https://github.com/playframework/play-slick
with  play 2.4, play-slick 1.0.0, postgresql 9.4-1201-jdbc41, slf4j 1.6.4.
1 - Configure your postgresql database in 
./conf/application.conf

slick.dbs.default.db.url="jdbc:postgresql://localhost/slick3comp"
slick.dbs.default.db.user= "yourUser"
slick.dbs.default.db.password="yourPssw"

2- create your postgresql db
* login:

sudo -u postgres psql postgres
* create the db:

CREATE DATABASE slick3comp;
* give permission to your usr:

GRANT ALL PRIVILEGES ON DATABASE slick3comp to yourUser;

3 - activator ~run

* Notes: Finally, I could reach a simple working example with database initialization, take a look at the files in
./app/bootstrap/. 
The first problem I faced with the original slick repository was that inspite the dependencies in sbt files were very nice/well programmed, I got lost in there. For the average person (like me) was too complicated to track what the all dependencies were for. So I've just adapted .sbt files from Play-Auth-Slick-Seed-master. 
After that the project still doesn't initialize the data ( the dependency injection so new to me) so I added some prints in there. I tried many things, the last was to insert a single Company value to the db. But it gave me the following error:
PSQLException: ERROR: null value in column "id" violates not-null constraint
So looking around I could make it work changing the id column (primary key) type to serial in the evolution files
/conf/evolutions/default/1.sql. The link is
https://groups.google.com/forum/?fromgroups=#%21topic/scalaquery/OEOF8HNzn2U 
Hope that not too far away Play and Typesafe team, could reach the easiness/user-friendly/whatever that communities like Arduino have: you face some error and after looking around for less than 5 minutes, you go straight to the point!

Enjoy!
ReneX
