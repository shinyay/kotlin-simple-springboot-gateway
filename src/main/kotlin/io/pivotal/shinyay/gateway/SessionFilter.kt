package io.pivotal.shinyay.gateway

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.session.SessionRepository
import org.springframework.stereotype.Component

@Component
class SessionFilter : ZuulFilter() {

    @Autowired
    lateinit var repository: SessionRepository<*>

    override fun run(): Any? {
        val context = RequestContext.getCurrentContext()
        val httpSession = context.request.session
        val session = repository.getSession(httpSession.id)

        context.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.id)
        println("SESSION ID Available: ${session.id}")
        return null
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun filterType(): String {
        return "pre"
    }

    override fun filterOrder(): Int {
        return 0
    }
}