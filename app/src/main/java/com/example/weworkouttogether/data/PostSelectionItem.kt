package com.example.weworkouttogether.data

import java.util.ArrayList

// 상위 리사이클러뷰 아이템클래스를 정의한다.
// 이때 리사이클러뷰 안에 하위리사이클러뷰 아이템으로 정의했던 SingleItem을 전역변수로 선언한다.
class PostSelectionItem(
    var headerTitle: String?,
    var singItemList: MutableList<PostSingleItem>
) {


}
