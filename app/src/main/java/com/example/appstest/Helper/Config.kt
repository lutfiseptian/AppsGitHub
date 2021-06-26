package com.example.appstest.Helper

object Config {
    private fun BASE_URL():String{
        return "https://api.github.com/search"
    }

    fun UserID():String{
        return BASE_URL()+"/users?q=a&per_page=10&page=1"
    }

    fun RepositoriID():String{
        return  BASE_URL()+"/repositories?q=a&per_page=10&page=1"
    }

    fun IssueID():String{
        return  BASE_URL()+"/issues?q=a&per_page=10&page=1"
    }

}