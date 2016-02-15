package com.knoldus.services

import java.sql._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
  * Created by knoldus on 12/2/16.
  */
object ConnectToDatabase {

  val logger:Logger = LoggerFactory.getLogger(this.getClass)

  def connect():Connection={

    logger.info("Entering Into Connect  Method to Connect to Database")
    val driver:String = "com.mysql.jdbc.Driver"
    val path:String = "jdbc:mysql://localhost/scalatest"
    val username:String = "root"
    val password:String = "root"

    try {
      Class.forName(driver)
      val connection: Connection = DriverManager.getConnection(path, username, password)
      logger.info("Connected To Database Successfully")
      connection
    }
    finally{
    }
  }
}
