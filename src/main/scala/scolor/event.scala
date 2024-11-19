package scolor.event

import com.raquo.laminar.api.L.{*, given}

object Event:
  def createTimeoutSignal(ms: Int): Signal[Option[Unit]] =
    EventStream.delay(ms).map(_ => Some(())).startWith(None)
  end createTimeoutSignal

  def createPeriodicCounter(ms: Int): EventStream[Int] =
    var counter = 0
    EventStream.periodic(ms).map(_ => {
      counter = counter + 1
      counter - 1
    })
  end createPeriodicCounter

end Event
