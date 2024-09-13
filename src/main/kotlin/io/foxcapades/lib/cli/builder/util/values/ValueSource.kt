package io.foxcapades.lib.cli.builder.util.values

import kotlin.reflect.KClass

/**
 * Represents or contains information about the origin of a value.
 *
 * @since 1.0.0
 */
interface ValueSource {
  /**
   * Short/simple name of the [ValueSource].
   *
   * If the source is a class member, this value will be the name of that class
   * member.
   *
   * If the source is anonymous, this value is dependent on context and
   * implementation.
   */
  val name: String

  val hasName: Boolean

  /**
   * Full reference to the [ValueSource].
   *
   * If the source is a class member, this value will contain the fully
   * qualified name of the class as well as the name of the specific class
   * member.
   *
   * If the source is anonymous, this value is dependent on context and
   * implementation.
   */
  val reference: String

  /**
   * Value source kind.
   */
  val kind: Kind

  /**
   * If the containing class for a [ValueSource] is known, it will be available
   * via this property.
   *
   * This property will always be `null` for [Kind.Anonymous], but may be
   * present for other [kind] values.
   */
  val containerType: KClass<*>?

  /**
   * If the containing instance for a [ValueSource] is known, it will be
   * available via this property.
   *
   * This property will always be `null` for [Kind.Anonymous], but may be
   * present for other [kind] values.
   */
  val instance: Any?

  enum class Kind {
    Property,
    Getter,
    Anonymous,
  }
}
