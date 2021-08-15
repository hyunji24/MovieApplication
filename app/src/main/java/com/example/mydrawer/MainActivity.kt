package com.example.mydrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mydrawer.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), FragmentCallback {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding

//    enum class FragmentItem{
//        ITEM1,ITEM2,ITEM3
//    }



    companion object{
        var requestQueue: RequestQueue?=null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue= Volley.newRequestQueue(applicationContext) //여기!

        val drawerLayout=findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView=findViewById<NavigationView>(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.nav_first-> {
                    Toast.makeText(this,"clicked first", Toast.LENGTH_LONG).show()
                    Log.d("MainActivity","first clicked")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM1,null)
                }
                R.id.nav_second-> {
                    Toast.makeText(this,"clicked second", Toast.LENGTH_LONG).show()
                    Log.d("MainActivity","second clicked")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM2,null)
                }
                R.id.nav_third-> {
                    Toast.makeText(this,"clicked third", Toast.LENGTH_LONG).show()
                    Log.d("MainActivity","third clicked")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM3,null)
                }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        requestBoxOffice()


        //supportFragmentManager.beginTransaction().add(R.id.frameLayout,Fragment1()).commit()
    }

    fun requestBoxOffice(){

        val apiKey="1e4a57d8da43f730f218f01b05b808f5"
        val targetDt="20210805"
        val url="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=${apiKey}&targetDt=${targetDt}"

        val request=object: StringRequest(
            Request.Method.GET,
            url,
            {
                println("\n응답->${it}")

                if(it.indexOf("faultInfo")>-1){
                    println("키 사용량이 초과됐다면 키를 발급받아 그 키로 사용하세요")
                }
                else{ processResponse(it)}
            },
            {
                println("\n에러->${it}")
                Log.d("MainActivity","${it.message}")
            }

        ){
            override fun getParams():MutableMap<String,String>{
                val params=HashMap<String,String>()
                params["userid"]="john"

                return params
            }
        }

        request.setShouldCache(false)
        requestQueue?.add(request)
        println("요청함")
    }

    fun processResponse(response:String){
        val gson= Gson()  //1. Gson객체 만들기
        val boxOffice=gson.fromJson(response,BoxOffice::class.java) //2. fromJson메서드 호출 (응답으로 받은 새로 정의한 BoxOffice클래스 객체)
        //boxOffice변수에는 BoxOffice객체가 할당되고 그 객체 안 속성 값은 JSON문자열에서 찾아낸 값으로 채워진다
        println("\n영화 수: "+boxOffice.boxOfficeResult.dailyBoxOfficeList.size)

        requestDetails(boxOffice.boxOfficeResult.dailyBoxOfficeList)

    }

    fun requestDetails(dailyBoxOfficeList:ArrayList<MovieInfo>){
        MovieList.data.clear()
        for (index in 0..4){
            var movieData= MovieData(dailyBoxOfficeList[index],null,null)
            MovieList.data.add(movieData)

            sendDetails(index,dailyBoxOfficeList[index].movieCd)
        }

    }

    fun sendDetails(index:Int,movieCd:String?){
        if(movieCd!=null){
            val apiKey="1e4a57d8da43f730f218f01b05b808f5"
            val url="http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${apiKey}&movieCd=${movieCd}"

            val request=object: StringRequest(
                Request.Method.GET,
                url,
                {
                    println("\n응답->${it}")
                    processDetailsResponse(index,it) //index 추가 맞나 TODO
                },
                {
                    println("\n에러->${it}")
                }

            ){}
            request.setShouldCache(false)
            requestQueue?.add(request)
            println("상세정보 요청함")

        }
    }

    fun processDetailsResponse(index:Int,response:String){
        val gson= Gson()
        val movieInfoDetails=gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails=movieInfoDetails.movieInfoResult.movieInfo

        println("\n영화제목,국가: ${movieDetails.movieNm},${movieDetails.movieNmEn},${movieDetails.nations[0].nationNm}")

        MovieList.data[index].movieDetails=movieDetails
        requestTMDBSearch(index,movieDetails)
    }

    fun requestTMDBSearch(index:Int,movieDetails: MovieDetails){
        var movieName=movieDetails.movieNm
        if(movieDetails.nations[0].nationNm!="한국"){
            movieName=movieDetails.movieNmEn
        }

        sendTMDBSearch(index,movieName)
    }

    fun sendTMDBSearch(index:Int,movieName:String?){
        if(movieName!=null){
            val apiKey="77affa75dd52cabfbfa87092f1966e46"
            val url="https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieName}&language=ko-KR&page=1"

            val request=object: StringRequest(
                Request.Method.GET,
                url,
                {
                    println("\n응답->${it}")

                    processTMDBSearchResponse(index,it) //index 추가 맞나 TODO
                },
                {
                    println("\n에러->${it}")
                }

            ){}
            request.setShouldCache(false)
            requestQueue?.add(request)
            println("영화검색 요청함")
        }
    }

    fun processTMDBSearchResponse(index: Int,response: String){
        val gson= Gson()
        val tmdbMovieDetails=gson.fromJson(response,TmdbMovieDetails::class.java)
        val movieResult=tmdbMovieDetails.results[0]

        println("\n영화 아이디,포스터,줄거리: ${movieResult.id},${movieResult.poster_path},${movieResult.overview}")

        MovieList.data[index].tmdbMovieResult=movieResult

        supportFragmentManager.beginTransaction().add(R.id.frameLayout,Fragment1()).commit()

        //setPosterImage(index,movieResult.poster_path)
    }

    override fun onFragmentSelected(item: FragmentCallback.FragmentItem, bundle: Bundle?){

        val index=bundle?.getInt("index",0)

        var fragment: Fragment
        when(item){
            FragmentCallback.FragmentItem.ITEM1->{
                fragment=Fragment1()
            }
            FragmentCallback.FragmentItem.ITEM2->{
                fragment=Fragment2.newInstance(index)
            }
            FragmentCallback.FragmentItem.ITEM3->{
                fragment=Fragment3()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}