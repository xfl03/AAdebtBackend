package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.json.aa.*

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
class AADebtServiceTest {

    @Autowired
    lateinit var service: AADebtService

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testAddGroup() {
        //Add Group
        var response = service.addGroup(AAnewRequest("test", listOf("p1", "p2")))
        assertThat(response, instanceOf(AAnewResponse::class.java))
        response = response as AAnewResponse
        assertThat(response.groupId, greaterThan(0))
        assertThat(response.name, equalTo("test"))
    }

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testGetGroup() {
        //Add Group
        service.addGroup(AAnewRequest("test", listOf("p1", "p2")))

        //Get Group
        var response = service.getGroups()
        assertThat(response, instanceOf(AAlistResponse::class.java))
        response = response as AAlistResponse
        assertThat(response.groups.size, `is`(1))
    }

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testGetPart() {
        //Add Group
        val res = service.addGroup(AAnewRequest("test", listOf("p1", "p2"))) as AAnewResponse

        //Get Part
        val response = service.getParts(AApartRequest(res.groupId))
        assertThat(response.parts.size, `is`(2))
    }

    @Test
    @Transactional
    @WithUserDetails(userDetailsServiceBeanName = "mockUserDetailsServiceBean")
    fun testAddDebt() {
        //Add Group
        val res = service.addGroup(AAnewRequest("test", listOf("p1", "p2"))) as AAnewResponse

        //Add Debt
        val response = service.addDebt(AAaddRequest(
                res.groupId, "test", res.parts[0].partId,
                listOf(
                        DetailInfo(res.parts[0].partId, 100),
                        DetailInfo(res.parts[1].partId, 100)
                )
        ))
        assertThat(response.amount, `is`(200))
    }
}