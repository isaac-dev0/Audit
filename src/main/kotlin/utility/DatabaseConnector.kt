package utility

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoClient

object DatabaseConnector {
    private const val CONNECTION_STRING = "mongodb+srv://audit-admin:Password123@university.lgjsaol.mongodb.net/"

    fun getMongoClient(): MongoClient {
        return MongoClients.create(CONNECTION_STRING)
    }
}