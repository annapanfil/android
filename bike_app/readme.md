# Aplikacja Rowerowa
Anna Panfil 145233
</br>gr. L8

Aplikacja napisana w kotlinie w ramach zajęć z aplikacji mobilnych na Politechnice Poznańskiej. Obsługuje trasy rowerowe w okolicach Poznania.

### Ekran powitalny
Na ekranie powitalnym wyświetla się animacja zachodzącego słońca.
Słońce porusza się w dół, zmieniają się też kolory nieba. 

*zmiana kolorów nieba*
```kotlin
val sunsetSkyAnimator = ObjectAnimator
    .ofInt(mSkyView!!, "BackgroundColor", mBlueSkyColor!!, mSunsetSkyColor!!)
    .setDuration(1000)
sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
```

Po animacji przechodzimy do głównej aktywności. Znajdują się tam 3 karty: z powitaniem użytkownika, trasami długimi i krótkimi.

W górnej części ekranu znajduje się pasek aplikacji w postaci paska narzędzi.  

*układ paska aplikacji*
```xml
<androidx.appcompat.widget.Toolbar xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="?attr/actionBarSize"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:background="?attr/colorPrimary"
android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
app:layout_scrollFlags="scroll|enterAlways" />
```

*obsługa paska aplikacji*
```kotlin
setSupportActionBar(toolbar)
pager.adapter = pagerAdapter

val tabLayout = findViewById<TabLayout>(R.id.tabs)
tabLayout.setupWithViewPager(pager)
```


Karty obsługiwane są przez adapter, zwracający odpowiednie fragmenty w zależności od wybranej karty. Karty można zmieniać klikając na ich tytuły lub gestem przesunięcia w lewo lub prawo.

*fragment SectionsPagerAdaptera, odpowiadający za zwrócenie odpowiedniego fragmentu*
```kotlin
when(position){
    0 -> return MainTabFragment()
    1 -> return TrackListFragment("long")
    2 -> return TrackListFragment("short")
}
```

Dane o trasach i ich statystykach przechowywane są w lokalnej bazie danych  




