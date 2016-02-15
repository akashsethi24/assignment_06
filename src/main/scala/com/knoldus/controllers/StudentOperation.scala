package com.knoldus.controllers

import java.sql._
import scala.concurrent.ExecutionContext.Implicits.global
import com.knoldus.services.ConnectToDatabase
import com.knoldus.domains.Student
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.Future

/**
  * Created by knoldus on 12/2/16.
  */
class StudentOperation {

  val logger:Logger = LoggerFactory.getLogger(this.getClass)

  def add(student:Student): Future[Boolean] = Future{
    logger.info("Entering Into Add Method of Student Operation Class")
    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "insert into student values (?,?,?,?)"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      prepareStatement.setString(1, student.student_id)
      prepareStatement.setString(2, student.student_name)
      prepareStatement.setString(3, student.student_email)
      prepareStatement.setLong(4, student.student_number)

      val number: Int = prepareStatement.executeUpdate()
      logger.info("In Between Add Method of Class Student Operation with " + number + " rows Affected")
      number match {
        case 1 => true
        case _ => false
      }
    }


    finally {
      logger.info("Closing Connection in Finally Block of Add Method of Class Student Operation")
      connection.close()
    }
  }

  def update(student:Student):Boolean={
    val connection:Connection = ConnectToDatabase.connect()
    val query:String = "update student set student_name = ?, student_email = ?, student_number = ? where student_id = ?"
    val prepareStatements: PreparedStatement = connection.prepareStatement(query)
    prepareStatements.setString(1,student.student_name)
    prepareStatements.setString(2,student.student_email)
    prepareStatements.setLong(3,student.student_number)
    prepareStatements.setString(4,student.student_id)
    val number = prepareStatements.executeUpdate()
    number match {
      case 1 => true
      case _ => false
    }
  }

  def delete(student_id:String):Future[Boolean] = Future{
    val connection: Connection = ConnectToDatabase.connect()
    val query1: String = "delete from student where student_id = ?"
    val query2: String = "delete from stud_sub where student_id = ?"
    try {
      val prepareStatements: PreparedStatement = connection.prepareStatement(query2)
      prepareStatements.setString(1,student_id)
      val number2: Int = prepareStatements.executeUpdate()
      logger.info("In Between Add Method of Class Student Operation with " + number2 + " rows Affected")
      true
    }
    finally {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query1)
      prepareStatement.setString(1,student_id)
      val number1: Int = prepareStatement.executeUpdate()
      println(number1)
      number1 match {
        case 1 => true
        case _ => false
      }
      logger.info("Closing Connection in Finally Block of Delete Method of Class Student Operation")
      connection.close()
    }
  }

  def read():List[Student]={

    logger.info("Closing Connection in Finally Block of Read Method of Class Student Operation")
    val connection: Connection = ConnectToDatabase.connect()
    val query: String = "select * from student"
    try {
      val prepareStatement: PreparedStatement = connection.prepareStatement(query)
      val resultSet:ResultSet = prepareStatement.executeQuery()
      def readList(resultSet: ResultSet,list: List[Student]):List[Student]={

        resultSet match {
          case rs if rs.next() => readList(rs, Student(rs.getString("student_id"), rs.getString("student_name"),rs.getString("student_email"),rs.getLong("student_number")) :: list)
          case _ => list
        }
      }
      readList(resultSet,List())
    }
    finally {
      logger.info("Closing Connection in Finally Block of Delete Method of Class Student Operation")
      connection.close()
    }
  }

}
