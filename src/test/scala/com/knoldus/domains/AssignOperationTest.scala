package com.knoldus.domains

import com.knoldus.controllers.AssignOperation
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by knoldus on 12/2/16.
  */
class AssignOperationTest extends  FunSuite with MockitoSugar{

  val obj = new AssignOperation

  test("Mockito test for Connection With Add Method"){
    val result:AssignOperation = mock[AssignOperation]
    when(result.add(Assign("",""))).thenReturn(Future{true})
    val output = Await.result(result.add(Assign("","")),10.seconds)
    assert(output === true)
  }

  test("Mockito test for Connection With Delete Method"){
    val result:AssignOperation = mock[AssignOperation]
    when(result.delete(1)).thenReturn(Future{true})
    val output = Await.result(result.delete(1),10.seconds)
    assert(output === true)
  }

  test("Simple Assignment") {
    val result = Await.result(obj.add(Assign("stu_1","sub_2")),10.seconds)
    assert(result === true)
  }

  test("Delete Assignment"){
    val result = Await.result(obj.delete(18),10.seconds)
    assert(result === true)
  }

  test("Read Assign Test"){
    val output = obj.read()
//    val result:List[Assign] = List(Assign("stu_1","sub_2"))
    assert(output.nonEmpty)
  }

}
