sealed trait Producer {
  def get(name: String): String
}

object SampleClass extends Producer {
  override def get(name: String): String = "Hello " + name
}
