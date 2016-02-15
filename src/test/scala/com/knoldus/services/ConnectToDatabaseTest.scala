package com.knoldus.services

import org.scalatest.FunSuite

/**
  * Created by knoldus on 14/2/16.
  */
class ConnectToDatabaseTest extends FunSuite{

  test("Test to Connect To Database"){
    val output = ConnectToDatabase.connect()
    assert(output.isValid(10))
  }
}
