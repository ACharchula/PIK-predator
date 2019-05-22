package com.pik.predator.helpers

import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.ok() {
    this.status = HttpServletResponse.SC_OK
}

fun HttpServletResponse.created() {
    this.status = HttpServletResponse.SC_CREATED
}

fun HttpServletResponse.notFound() {
    this.status = HttpServletResponse.SC_NOT_FOUND
}