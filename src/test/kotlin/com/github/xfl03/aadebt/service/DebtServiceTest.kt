package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.json.aa.AAlistResponse
import com.github.xfl03.aadebt.json.debt.*

import org.hamcrest.Matchers.*

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
class DebtServiceTest {

    @Autowired
    lateinit var service: DebtService
    @Autowired
    lateinit var aaService: AADebtService

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testAddGroup() {
        //Add Group
        val res = service.addGroup(DebtNewRequest("test"))
        assertThat(res.code, `is`(200))
    }

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testGetGroup() {
        //Add Group
        service.addGroup(DebtNewRequest("test"))

        //Get Group
        var response = aaService.getGroups()
        assertThat(response, instanceOf(AAlistResponse::class.java))
        response = response as AAlistResponse
        assertThat(response.groups.size, `is`(1))
    }

}