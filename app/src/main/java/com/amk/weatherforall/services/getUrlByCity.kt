package com.amk.weatherforall.services

import android.content.Context
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City

fun getUrlByCity(city: City, context:Context):String{
    return when(city.name){
        context.resources.getString(R.string.Moscow) -> "https://images.unsplash.com/photo-1594805986553-e110b2d793f3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
        context.resources.getString(R.string.Saratov) -> "https://images.unsplash.com/photo-1600799651038-4c24e1f23bc3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
        context.resources.getString(R.string.Saint_Petersburg) -> "https://images.unsplash.com/photo-1556610961-2fecc5927173?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2120&q=80"
        context.resources.getString(R.string.Novosibirsk) -> "https://cdn.pixabay.com/photo/2018/10/02/03/48/bridge-3717836_960_720.jpg"
        context.resources.getString(R.string.Ekaterinburg) -> "https://cdn.pixabay.com/photo/2020/07/18/18/44/city-5418025_960_720.jpg"
        context.resources.getString(R.string.Kazan) -> "https://cdn.pixabay.com/photo/2018/03/15/10/54/kazan-3227834_960_720.jpg"
        context.resources.getString(R.string.Chelyabinsk) -> "https://upload.wikimedia.org/wikipedia/commons/2/27/%D0%A1%D0%BA%D0%B2%D0%B5%D1%80.%D0%BD%D0%B0.%D0%90%D0%BB%D0%BE%D0%BC.%D0%BF%D0%BE%D0%BB%D0%B5%28%D0%A7%D0%B5%D0%BB%D1%8F%D0%B1%D0%B8%D0%BD%D1%81%D0%BA%29.jpg"
        context.resources.getString(R.string.Samara) -> "https://upload.wikimedia.org/wikipedia/commons/9/95/%D0%9A%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%BE-%D0%9C%D0%B5%D1%84%D0%BE%D0%B4%D0%B8%D0%B5%D0%B2%D1%81%D0%BA%D0%B8%D0%B9_%D1%81%D0%BE%D0%B1%D0%BE%D1%80_%28%D0%A1%D0%B0%D0%BC%D0%B0%D1%80%D0%B0%29.jpg"
        context.resources.getString(R.string.Omsk) -> "https://cdn.pixabay.com/photo/2018/11/09/10/38/city-3804249_960_720.jpg"
        context.resources.getString(R.string.Rostov_on_don) -> "https://cdn.pixabay.com/photo/2019/12/09/19/56/sunset-4684265_960_720.jpg"
        context.resources.getString(R.string.Ufa) -> "https://cdn.pixabay.com/photo/2020/04/22/04/35/marten-5075824_960_720.jpg"
        context.resources.getString(R.string.Krasnoyarsk) -> "https://cdn.pixabay.com/photo/2020/01/02/16/24/krasnoyarsk-4736231_960_720.jpg"
        context.resources.getString(R.string.Voronezh) -> "https://cdn.pixabay.com/photo/2014/10/31/18/15/voronezh-510882_960_720.jpg"
        context.resources.getString(R.string.Perm) -> "https://cdn.pixabay.com/photo/2017/09/22/05/59/the-city-of-perm-2774546_960_720.jpg"
        context.resources.getString(R.string.Volgograd) -> "https://cdn.pixabay.com/photo/2020/05/25/06/22/volgograd-5217087_960_720.jpg"
        context.resources.getString(R.string.Krasnodar) -> "https://cdn.pixabay.com/photo/2018/10/21/12/28/krasnodar-3762807_960_720.jpg"
        context.resources.getString(R.string.Tyumen) -> "https://cdn.pixabay.com/photo/2016/06/12/17/40/city-1452548_960_720.jpg"
        context.resources.getString(R.string.Izhevsk) -> "https://cdn.pixabay.com/photo/2018/09/19/12/39/the-urban-landscape-3688441_960_720.jpg"
        context.resources.getString(R.string.Barnaul) -> "https://cdn.pixabay.com/photo/2019/10/17/02/03/barnaul-4555772_960_720.jpg"
        context.resources.getString(R.string.Ulyanovsk) -> "https://upload.wikimedia.org/wikipedia/commons/b/b0/%D0%A1%D0%BF%D0%B0%D1%81%D0%BE-%D0%92%D0%BE%D0%B7%D0%BD%D0%B5%D1%81%D0%B5%D0%BD%D1%81%D0%BA%D0%B8%D0%B9_%D0%BA%D0%B0%D1%84%D0%B5%D0%B4%D1%80%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9_%D1%81%D0%BE%D0%B1%D0%BE%D1%80_%28%D0%A3%D0%BB%D1%8C%D1%8F%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%29.jpg"
        context.resources.getString(R.string.Irkutsk) -> "https://cdn.pixabay.com/photo/2015/08/28/12/36/irkutsk-911832_960_720.jpg"
        context.resources.getString(R.string.Khabarovsk) -> "https://cdn.pixabay.com/photo/2015/09/15/04/27/khabarovsk-940458_960_720.jpg"
        context.resources.getString(R.string.Yaroslavl) -> "https://cdn.pixabay.com/photo/2017/08/09/20/17/river-2615647_960_720.jpg"
        context.resources.getString(R.string.Vladivostok) -> "https://cdn.pixabay.com/photo/2018/10/09/10/33/fefu-3734649_960_720.jpg"
        context.resources.getString(R.string.Kotlas) -> "https://upload.wikimedia.org/wikipedia/commons/1/11/Saint_Stephen_of_Perm_Church_%28Kotlas%29_%2801%29.JPG"
        context.resources.getString(R.string.Vladivostok) -> "https://cdn.pixabay.com/photo/2018/10/09/10/33/fefu-3734649_960_720.jpg"
        context.resources.getString(R.string.Sertolovo) -> "https://cdn.pixabay.com/photo/2020/11/13/09/50/city-5738104_960_720.jpg"
        context.resources.getString(R.string.Kuloy) -> "https://cdn.pixabay.com/photo/2020/11/19/09/59/09-59-33-165_960_720.jpg"
        else -> "https://images.unsplash.com/uploads/1413259835094dcdeb9d3/6e609595?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1352&q=80"
    }
}