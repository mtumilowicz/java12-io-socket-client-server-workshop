package answers 
/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step1_SingleThreadedServerAnswerTest extends ServerTest {

    @Override
    def getServer() {
        new Step1_SingleThreadedServerAnswer()
    }
}
