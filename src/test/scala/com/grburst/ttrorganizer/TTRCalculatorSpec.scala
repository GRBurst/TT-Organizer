import org.specs2._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class QuickStartSpec extends Specification {
  def is = s2"""

 This is my first specification
   it is working                 $ok
   really working!               $ok
                                 """
}
