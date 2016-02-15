package com.knoldus.domains

import com.knoldus.controllers.SubjectOpearation
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by knoldus on 12/2/16.
  */
class SubjectOpearationTest extends FunSuite with MockitoSugar{

  val obj = new SubjectOpearation

  test("Mockito test for Connection with Add Method"){
    val result:SubjectOpearation = mock[SubjectOpearation]
    when(result.add(Subject("",""))).thenReturn(Future{true})
    val output = Await.result(result.add(Subject("","")),10.seconds)
    assert(output === true)
  }

  test("Mockito test for Connection for Delete Method"){
    val result:SubjectOpearation = mock[SubjectOpearation]
    when(result.delete("sub_1")).thenReturn(Future{true})
    val output = Await.result(result.delete("sub_1"),10.seconds)
    assert(output === true)
  }

  test("Simple Add For Subject"){
    val output = Await.result(obj.add(Subject("sub_1","C Language")),10.seconds)
    assert(output === true)
  }

  test("Delete Subject Test"){
    val output = Await.result(obj.delete("sub_1"),10.seconds)
    assert(output === true)
  }
  test("Read Subject Test"){
    val output = obj.read()
    val result:List[Subject] = List(Subject("sub_2","Scala"))
    assert(result === output)
  }
  test("Update Subject Test"){
    val output = obj.update(Subject("sub_2","Scala"))
    assert(output === true)
  }

}
