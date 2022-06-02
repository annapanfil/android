package com.example.bike_app

class Route(val name: String, val imgResourceId: Int) {
    companion object {
        val routes: Array<Route> = arrayOf(
            Route("cysterski_szlak_rowerowy", R.drawable.cysterski_szlak_rowerowy),
            Route("dolina_cybiny_do_parku_krajobrazowego_promno", R.drawable.dolina_cybiny_do_parku_krajobrazowego_promno),
            Route("pierscien_rowerowy_dookola_poznania", R.drawable.pierscien_rowerowy_dookola_poznania)
        )
    }
}