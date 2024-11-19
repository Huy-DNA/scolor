package scolor.event

import com.raquo.laminar.api.L.{*, given}

object Event:
  def createValueTransition[T](ms: Int, from: T, to: T): Signal[T] =
    EventStream.delay(ms).map(_ => to).startWith(from)
  end createValueTransition

  def createPeriodicCounter(ms: Int, initialValue: Int = 0, updater: (Int) => Int = 1 + _): EventStream[Int] =
    var counter = initialValue
    EventStream.periodic(ms).map(_ => {
      val oldCounter = counter
      counter = updater(counter)
      oldCounter
    })
  end createPeriodicCounter

end Event
