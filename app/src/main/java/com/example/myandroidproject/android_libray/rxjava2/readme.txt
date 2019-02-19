
------------------RXJava---------------------

rxjava响应式编程：
特点：
	1：线程切换（异步），
	2.事件变换（String->Bitmap）
	3.链式调度（避免了回调+嵌套）

RxJava 有四个基本概念：
	Observable： 即被观察者，它决定什么时候触发事件以及触发怎样的事件
	Observer： 即观察者，它决定事件触发的时候将有怎样的行为。
	事件：
	Subscribe (订阅)：Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
		创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了
	从而 Observable 可以在需要的时候发出事件来通知 Observer。

线程切换：

--Scheduler:线程调度器
	--IOScheduler:子线程调度器
	--HandlerScheduler:主线程调度器。
--Work

子线程->new Thread();
主线程：Looper.getMainLoop();
handler.postRunnable();
