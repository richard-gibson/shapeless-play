/*
 * Copyright year author
 */
package brightcog.typefun

/**
  * Created by richardgibson on 12/02/2017.
  */
object VBool {

  sealed trait Bool {
    def &&(b: Bool): Bool
    def ||(b: Bool): Bool
    def ifElse[B](t: => B, f: => B): B
  }

  lazy val True: Bool = new Bool {
    override def ||(b: Bool)                 = True
    override def &&(b: Bool)                 = b
    override def ifElse[B](t: => B, f: => B) = t
  }

  lazy val False: Bool = new Bool {
    override def ||(b: Bool)                 = b
    override def &&(b: Bool)                 = False
    override def ifElse[B](t: => B, f: => B) = f
  }

  //a b c -> a.b(c)
  assert((False && False) == False)
  assert((False && True) == False)
  assert((True && False) == False)
  assert((True && True) == True)

  assert((False || False) == False)
  assert((False || True) == True)
  assert((True || False) == True)
  assert((True || True) == True)

  assert(False.ifElse(1, 2) == 2)
  assert(True.ifElse(1, 2) == 1)

}

object TBool {
  sealed trait Bool {
    type &&[B <: Bool] <: Bool
    type ||[B <: Bool] <: Bool
    type IfElse[B, T <: B, F <: B] <: B
  }

  type True  = True.type
  type False = False.type

  object True extends Bool {
    type &&[B <: Bool]             = B
    type ||[B <: Bool]             = True
    type IfElse[B, T <: B, F <: B] = T
  }

  object False extends Bool {
    type &&[B <: Bool]             = False
    type ||[B <: Bool]             = B
    type IfElse[B, T <: B, F <: B] = F
  }
  //A B C -> B[A, C]

//  implicitly[False#||[True] =:= True]
  //
  //  implicitly[(False#&&[False]) =:= False]
  //  // is the same as
  //  implicitly[=:=[(False#&&[False]), False]]
  //
  //  implicitly[(False#&&[True]) =:= False]
  //  implicitly[(True#&&[False]) =:= False]
  //  implicitly[(True#&&[True]) =:= True]
  //
  //  implicitly[(False#||[False]) =:= False]
  //  implicitly[(False#||[True]) =:= True]
  //  implicitly[(True#||[False]) =:= True]
  //  implicitly[(True#||[True]) =:= True]

  implicitly[False#IfElse[Any, Int, String] =:= String]
  implicitly[True#IfElse[Any, Int, String] =:= Int]

}
