package com.knoldus.domains

import com.knoldus.controllers.StudentOperation
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by knoldus on 12/2/16.
  */
class StudentOperationTest extends FunSuite with MockitoSugar{

  val obj = new StudentOperation

  test("Mockito test for Connection With Add Method"){
    val result:StudentOperation = mock[StudentOperation]
    when(result.add(Student("","","",0L))).thenReturn(Future{true})
    val output = Await.result(result.add(Student("","","",0L)),10.seconds)
    assert(output === true)
  }

  test("Mockito test for Connection With Delete Method"){
    val result:StudentOperation = mock[StudentOperation]
    when(result.delete("stu_1")).thenReturn(Future{true})
    val output = Await.result(result.delete("stu_1"),10.seconds)
    assert(output === true)
  }

  test("Simple Add Student Test"){
    val output = Await.result(obj.add(Student("stu_2","Kunal Kapoor","kunal.kapoor@knoldus.in",8471050451L)),10.seconds)
    assert(output === true)
  }

  test("Delete Student Test"){
    val output = Await.result(obj.delete("stu_2"),10.seconds)
    assert(output === true)
  }
  test("Read Student Test"){
    val output = obj.read()
//    val result:List[Student] = List(Student("stu_1","Akash Sethi","akash.sethi@hotmail.com",9808344790L))
    assert(output.nonEmpty)
  }
  test("Update Student Test"){
    val output = obj.update(Student("stu_1","Akash","akash@gmail.com",9808344790L))
    assert(output === true)
  }

}
