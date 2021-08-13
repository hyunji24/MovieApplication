package com.example.mydrawer

data class MovieDetails(

    val movieCd:String?,
    val movieNm:String?,
    val movieNmEn:String?,
    val prdtYear:String?,
    val showTm:String?,
    val openDt:String?,
    val typeNm:String?,
    val nations:ArrayList<NationInfo>
    =ArrayList<NationInfo>(),
    val genres:ArrayList<GenreInfo>
    =ArrayList<GenreInfo>(),
    val directors:ArrayList<DirectorInfo>
    =ArrayList<DirectorInfo>(),
    val actors:ArrayList<ActorInfo>
    =ArrayList<ActorInfo>(),
    val audits:ArrayList<AuditInfo>
    =ArrayList<AuditInfo>(),
    val companies:ArrayList<CompanyInfo>
    =ArrayList<CompanyInfo>()




)
