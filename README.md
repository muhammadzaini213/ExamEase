# ExamEase
ExamEase is an open source project that intended to create an exam application that support teacher to held online exam/class/announcement online and prevent student from cheating with their device without make an malicious permission.

# Why we make this
Its because we are know many school using malicious app to held exam just to make sure their student never cheats, but most of that aren't working properly and some dev even planted spyware to their app so they can profit.

# What feature that we will add
We are deciding to add basic feature like onWindowFocusChanged, onPause, etc for detection, but we wont ever make loud alarm sound and startLockTask because in many cases, those method is the one who causes student device stuck or even worse, bootloop.

# So what is our plan to stop cheating or open another app while exam?
We let student do it, no loud alarm sound, no lockScreen/startLockTask(). But, we will add detection and sent it to teachers/admin device. How many times they do that and teacher can decrease student scores by knowing the are cheating the entire exam. So with this, student would never try to cheat. UNLESS, they can reverse engineering this app.
