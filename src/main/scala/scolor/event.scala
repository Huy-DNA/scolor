package scolor.event

import com.raquo.laminar.api.L.{*, given}

object Event:
  def createValueTransition[T](ms: Int, from: T, to: T): Signal[T] =
    EventStream.delay(ms).map(_ => to).startWith(from)
  end createValueTransition

  def createPeriodicCounter(ms: Int): EventStream[Int] =
    var counter = 0
    EventStream.periodic(ms).map(_ => {
      counter = counter + 1
      counter - 1
    })
  end createPeriodicCounter

end Event
