package com.harismexis.practice.parser

abstract class BaseFileParser {

    abstract fun getFileAsString(filePath: String): String

}