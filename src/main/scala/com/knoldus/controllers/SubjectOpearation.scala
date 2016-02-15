package com.knoldus.controllers

import java.sql._
import scala.concurrent.ExecutionContext.Implicits.global
import com.knoldus.services.ConnectToDatabase
import com.knoldus.domains.Subject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.Future

/**
  * Created by knoldus on 12/2/16.
  */
class SubjectOpearation {

  val logger:Logger = LoggerFactory.getLogger(this.getClass)

  def add(subject:Subject): Future[Boolean] =Future {
    logger.info("Entering Into Add Method of Subject Operation Class")
    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "insert into subject values (?,?)"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      prepareStatement.setString(1,subject.subject_id)
      prepareStatement.setString(2,subject.subject_name)
      val number: Int = prepareStatement.executeUpdate()
      logger.info("In Between Add Method of Class Subject Operation with " + number + " rows Affected")
      number match {
        case 1 => true
        case _ => false
      }
  }
    finally {
      logger.info("Closing Connection in Finally Block of Add Method of Class Subject Operation")
    connection.close()
    }
  }

  def delete(subject_id:String):Future[Boolean] = Future{
    val connection: Connection = ConnectToDatabase.connect()
    val query1: String = "delete from subject where subject_id = ?"
    val query2: String = "delete from stud_sub where subject_id = ?"
    try {
      val prepareStatements: PreparedStatement = connection.prepareStatement(query2)
      prepareStatements.setString(1,subject_id)
      val number2: Int = prepareStatements.executeUpdate()
      true
    }
    finally {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query1)
      prepareStatement.setString(1,subject_id)
      val number1: Int = prepareStatement.executeUpdate()

      number1 match {
        case 1 => true
        case _ => false
      }
      logger.info("Closing Connection in Finally Block of Delete Method of Class Subject Operation")
      connection.close()
    }
  }

  def read():List[Subject]={

    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "select * from subject"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet:ResultSet = prepareStatement.executeQuery()
      def readList(resultSet: ResultSet,list: List[Subject]):List[Subject]={

          resultSet match {
            case rs if rs.next() => readList(rs, Subject(rs.getString("subject_id"), rs.getString("subject_name")) :: list)
            case _ => list
          }
      }
      readList(resultSet,List())
  }
    finally {
      logger.info("Closing Connection in Finally Block of Read Method of Class Subject Operation")
      connection.close()
    }
  }

  def update(subject:Subject):Boolean={
    val connection:Connection = ConnectToDatabase.connect()
    val query:String = "update subject set subject_name = ? where subject_id = ?"
    val prepareStatements: PreparedStatement = connection.prepareStatement(query)
    prepareStatements.setString(1,subject.subject_name)
    prepareStatements.setString(2,subject.subject_id)
    val number = prepareStatements.executeUpdate()
    number match {
      case 1 => true
      case _ => false
    }
  }

}