package com.pik.predator.databaseClasses

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Orders")
class Order(order_id: Int, e_mail: String, first_name: String, last_name: String, street: String,
            house_number: String, local_number: String, postal_code: String, city: String,
            payment_method: String, products: List<BasicNotebookInfo>) {

    @Id
    var order_id: Int = order_id
    var e_mail: String = e_mail
    var first_name: String = first_name
    var last_name: String = last_name
    var street: String = street
    var house_number: String = house_number
    var local_number: String = local_number
    var postal_code: String = postal_code
    var city: String = city
    var payment_method: String = payment_method
    var products: List<BasicNotebookInfo> = products
}