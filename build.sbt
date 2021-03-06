
import Resolvers._
import sbt.Keys.version

name := "kf-portal-etl"


lazy val commonSettings = Seq(
  organization := "io.kf.etl",
  version := "0.1.0",
  scalaVersion := "2.11.12",
  resolvers ++= Seq(
    clojars,
    maven_local,
    novus,
    twitter,
    spark_packages,
    artima
  ),
  test in assembly := {},

  assemblyMergeStrategy in assembly := {
    case PathList("io", "netty", xs @ _*) => MergeStrategy.last
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
    case PathList("javax", "ws", xs @ _*) => MergeStrategy.last
    case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
    case PathList("com", "sun", "research", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", "commons", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", "hadoop", xs @ _*) => MergeStrategy.last
    case PathList("org", "aopalliance", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", "spark", "unused", xs @ _*) => MergeStrategy.last
    case PathList("javax", "annotation", xs @ _*) => MergeStrategy.first
    case "META-INF/io.netty.versions.properties" => MergeStrategy.last
    case "META-INF/native/libnetty_transport_native_epoll_x86_64.so" => MergeStrategy.last
    case "META-INF/DISCLAIMER" => MergeStrategy.last
    case "mozilla/public-suffix-list.txt" => MergeStrategy.last
    case "overview.html" => MergeStrategy.last
    case "git.properties" => MergeStrategy.discard
    case "mime.types" => MergeStrategy.first
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },

  assemblyExcludedJars in assembly := {
    val regex = """.*spark.*2\.11\-2.3.0.*""".r
    val cp = (fullClasspath in assembly).value
    cp.filter(f => {
      regex.pattern.matcher( f.data.getName ).matches()
    })
  },

  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
)

lazy val root = (project in file(".")).aggregate(model, common, processors, pipeline)

lazy val model = (project in file("kf-portal-etl-model")).settings(commonSettings:_*)

lazy val common = (project in file("kf-portal-etl-common")).dependsOn(model).settings(commonSettings:_*)

lazy val processors = (project in file("kf-portal-etl-processors")).dependsOn(common%"test->test;compile->compile").settings(commonSettings:_*)

lazy val pipeline = (project in file("kf-portal-etl-pipeline")).dependsOn(processors).settings(commonSettings:_*)

lazy val livy = (project in file("kf-portal-etl-livy")).dependsOn(pipeline).settings(commonSettings:_*)


