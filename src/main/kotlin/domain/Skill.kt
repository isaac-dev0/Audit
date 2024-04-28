package domain

import domain.enum.Proficiency
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Skill(
    @BsonId
    val _id: ObjectId? = null,
    val title: String,
    val description: String?,
    val category: String?,
    val expiryDate: String?,
    val proficiency: Proficiency?,
    val notes: String?
) {

    fun getId(): ObjectId? = _id

}