package com.knoldus.controllers

import java.sql.{ResultSet, PreparedStatement, Connection}
import scala.concurrent.ExecutionContext.Implicits.global
import com.knoldus.services.ConnectToDatabase
import com.knoldus.domains.Assign
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.concurrent.Future

/**
  * Created by knoldus on 12/2/16.
  */
class AssignOperation {

  val logger:Logger = LoggerFactory.getLogger(this.getClass)

  def add(assign:Assign): Future[Boolean] =Future{
    logger.info("Entering Into Add Method of Assign Operation Class")
    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "insert into stud_sub(student_id,subject_id) values (?,?)"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      prepareStatement.setString(1,assign.stu_id)
      prepareStatement.setString(2,assign.sub_id)
      val number: Int = prepareStatement.executeUpdate()

      logger.info("In Between Add Method of Class Assign Operation with " + number + " rows Affected")
      number match {
        case 1 => true
        case _ => false
      }
    }
    finally {
      logger.info("Closing Connection in Finally Block of Add Method of Class Assign Operation")
      connection.close()
    }
  }

  def delete(ss_id:Int):Future[Boolean]=Future{


      logger.info("Entering Into Delete Method of Assign Operation Class")
      val connection: Connection = ConnectToDatabase.connect()
      val query: String = "delete from stud_sub where ss_id = ?"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      prepareStatement.setInt(1, ss_id)
      val number: Int = prepareStatement.executeUpdate()
      logger.info("In Between Delete Method of Class Assign Operation with " + number + " rows Affected")
      number match {
        case 1 => true
        case _ => false
      }
    }
    finally {
      logger.info("Closing Connection in Finally Block of Delete Method of Class Assign Operation")
      connection.close()
    }
  }

  def read():List[Assign]={

    logger.info("Entering Into Read Method of Assign Operation Class")
    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "select * from stud_sub"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet:ResultSet = prepareStatement.executeQuery()
      def readList(resultSet: ResultSet,list: List[Assign]):List[Assign]={

        resultSet match {
          case rs if rs.next() => readList(rs, Assign(rs.getString("student_id"), rs.getString("subject_id")) :: list)
          case _ => list
        }
      }
      logger.info("In Between Read Method of Class Assign Operation ")
      readList(resultSet,List())
    }
    finally {
      logger.info("Closing Connection in Finally Block of Read Method of Class Assign Operation")
      connection.close()
    }
  }

}
