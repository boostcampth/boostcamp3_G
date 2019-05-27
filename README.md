 <div><a href="https://github.com/boostcampth/boostcamp3_G/wiki"><img src="https://user-images.githubusercontent.com/28249981/52176099-51884f00-27f1-11e9-88c6-07e549e44ba7.png"/></a></div>
 
 # 정해드림 
  
### 결정장애, 선택장애가 있는 사람들을 위한 SNS 실시간 투표 앱

- 옷을 고를때나 약속 장소를 정할 때 등 선택에 어려움을 느끼시나요?

- 정해드림 앱에 고민을 올리시면 다른 사용자들의 실시간 투표를 통해 선택을 도와드리겠습니다!

## 안내사항
## _현재 배포가 되어있지 않아 실행이 불가합니다.(로그인 불가)_

<hr>

### 목차
- [팀 구성원](#팀-구성원)
- [프로젝트 개요](#프로젝트-개요)
  - 기획 의도
  - 주요 기능
  - 차별점
- [스크린샷](#스크린샷)
- [프로젝트 설계](#프로젝트-설계)
  - 아키텍처
  - 데이터베이스
  - 라이브러리
- [사용 기술](#사용-기술)
- [References](#References)

***

### 팀 구성원
- [윤영직](https://github.com/tbtzpdlql)
- [이예슬](https://github.com/dptmf7705)
- [이대근](https://github.com/leedaegun)


***


### 프로젝트 개요
1. 기획 의도

옷을 고를때나 약속 장소를 정할 때 등 어떤 것을 선택하는데 고민이 될 때 다른 사람들의 의견을 물어보고 싶을 때가 있습니다. 

이때 앱에 투표 작성을 통해 고민을 올리면 다른 사용자들과 해당 고민에 대해 실시간 투표를 합니다. 

다양한 사람들의 의견을 투표결과를 통해 보면서 사용자가 결정을 하는데 도움을 주고자 기획하게 되었습니다.

2. 주요 기능

정해드림은 고민을 실시간투표을 통해 즉각적인 소통이 가능합니다.

- 메인 피드
  - 현재 올라와 있는 게시물을 볼 수 있다.
  - 또한 투표를 하게되면 해당 게시물의 투표 현황을 볼 수 있다.
  - 리스트를 클릭하게 되면 상세정보를 볼 수 있습니다.
- 업로드
  - 고민거리를 2장의 사진을 통해 게시물을 업로드합니다.
  - 사진은 카메라와 앨범을 통해 업로드 할 수 있습니다.
  - 게시물에는 고민에 대한 설명과 태그를 추가할 수 있습니다.
- 결과 보기
  - 본인이 투표한 게시물들을 볼 수 있습니다.
  - 리스트를 클릭하게 되면 상세정보를 볼 수 있습니다.
- 프로필
  - 본인이 업로드한 게시물을 볼 수 있습니다.
  - 본인이 업로드한 게시물의 투표를 종료할 수 있습니다.
  - 리스트를 클릭하게 되면 상세정보를 볼 수 있습니다.
- 상세보기
  - 게시물의 이미지를 보다 큰 화면으로 볼 수 있습니다.
  - 상세보기 화면에서도 동일하게 투표가 가능합니다.
  - 투표가 종료되었다면 종료메시지가 보여지며, 투표는 할 수 없습니다.
  
3. 차별점

기존 SNS는 다양한 방법으로 사용자들간 소통을 할 수 있습니다..

그래서 정해드림은 이를 보다 더 간단하게 소통을 할 방법을 모색했습니다.

다른 사용자의 고민을 내 일 처럼생각하고 투표를 통해 자신의 의견을 전달하면서 보다 사용자간의 소통을 보다 간단하게 할 수 있습니다. 

이에 투표 참여율을 높이기 위해 투표기능에 에니메이션 기능을 추가함으로써 사용자들의 참여율을 높이고자 하였습니다.

***

### 스크린샷
<p align="center">
<img src="https://user-images.githubusercontent.com/28249981/55798722-a2c30080-5b0a-11e9-9345-2ce8fa723c41.gif" width=360 height=300/>
<img src="https://user-images.githubusercontent.com/28249981/55843089-6c6c9c00-5b71-11e9-9cd0-bf7c3b4a4cfe.gif" width=360 height=300/>
</p>

***


### 사용 기술
1. 아키텍처
   - Google MVVM Pattern
2. 데이터베이스
   - Firebase-Firestore
3. 라이브러리
   - [RxJava](https://github.com/ReactiveX/RxAndroid)
   - [LiveData & ViewModel](https://developer.android.com/topic/libraries/architecture/adding-components)
   - [Bottom Navigation View](https://github.com/material-components/material-components-android)
   - RecyclerView & CardView
   - [Glide](https://github.com/bumptech/glide)
   - [Firebase & Firestore](https://firebase.google.com/docs/android/setup)
   - [Shine Button](https://github.com/ChadCSong/ShineButton)
   - [Android Round Corner ProgressBar](https://github.com/akexorcist/Android-RoundCornerProgressBar)
   - [Ted Permission](https://github.com/ParkSangGwon/TedPermission)
   - [Ted Bottom Picker](https://github.com/ParkSangGwon/TedBottomPicker)
   - [Ted RxOnActivityForResult](https://github.com/ParkSangGwon/TedRxOnActivityResult)
   - [Tag Group](https://github.com/2dxgujun/AndroidTagGroup)
   - [Retrofit](https://github.com/square/retrofit)
   - [Okhttp](https://github.com/square/okhttp)
   - [Leakcanary](https://github.com/square/leakcanary)
   - [Dagger](https://github.com/google/dagger)

***

### References
- [Android Style Guide](https://github.com/PRNDcompany/android-style-guide)
- [Git Learning Branch](https://learngitbranching.js.org/)
- [App Architecture Guide](https://developer.android.com/jetpack/docs/guide#fetching_data)
- [Cloud Firestore](https://firebase.google.com/docs/firestore/quickstart)
***
