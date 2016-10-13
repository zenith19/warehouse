name := "warehouse"

version := "1.0"

lazy val `warehouse` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
  "com.datastax.cassandra" % "cassandra-driver-mapping" % "3.1.0",
  "com.datastax.cassandra" % "cassandra-driver-extras" % "3.1.0",
  "com.impetus.kundera.client" % "kundera-cassandra-ds-driver" % "3.5", filters
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  