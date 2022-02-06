package im.koala.domain.usecase.keyword

import javax.inject.Inject

class SetAlarmSiteListUseCase @Inject constructor() {
    operator fun invoke(command:String,site:String,alarmSiteList:List<String>){
        when(command){
            "add" -> {

            }
            "delete" -> {

            }
        }
    }
}