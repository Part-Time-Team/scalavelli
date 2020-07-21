import org.scalatest.funspec.AnyFunSpec

class SampleClassSpec extends AnyFunSpec {
  describe("The result of a producer") {
    it ("should say hello to the user") {
      assert("Hello World" equals (SampleClass get "World"))
    }
  }
}
