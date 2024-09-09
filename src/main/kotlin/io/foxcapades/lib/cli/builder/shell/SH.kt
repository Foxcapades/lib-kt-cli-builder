package io.foxcapades.lib.cli.builder.shell

import io.foxcapades.lib.cli.builder.util.CharacterAppender
import io.foxcapades.lib.cli.builder.util.isPrintable

private const val CHAR_NUL = '\u0000'    // ASCII 0
private const val CHAR_SPACE = ' '       // ASCII 32
private const val CHAR_QUOT = '"'        // ASCII 34
private const val CHAR_DOLLAR = '$'      // ASCII 36
private const val CHAR_AMP = '&'         // ASCII 38
private const val CHAR_APOS = '\''       // ASCII 39
private const val CHAR_AST = '*'         // ASCII 42
private const val CHAR_COMMA = ','       // ASCII 44
private const val CHAR_DIGIT_ZERO = '0'  // ASCII 48
private const val CHAR_SEMI = ';'        // ASCII 59
private const val CHAR_LT = '<'          // ASCII 60
private const val CHAR_GT = '>'          // ASCII 62
private const val CHAR_QUEST = '?'       // ASCII 63
private const val CHAR_UPPER_A = 'A'     // ASCII 65
private const val CHAR_LSQB = '['        // ASCII 91
private const val CHAR_BSOL = '\\'       // ASCII 92
private const val CHAR_HAT = '^'         // ASCII 94
private const val CHAR_GRAVE = '`'       // ASCII 96
private const val CHAR_LOWER_A = 'a'     // ASCII 97
private const val CHAR_LOWER_B = 'b'     // ASCII 98
private const val CHAR_LOWER_E = 'e'     // ASCII 101
private const val CHAR_LOWER_F = 'f'     // ASCII 102
private const val CHAR_LOWER_N = 'n'     // ASCII 110
private const val CHAR_LOWER_R = 'r'     // ASCII 114
private const val CHAR_LOWER_T = 't'     // ASCII 116
private const val CHAR_LOWER_V = 'v'     // ASCII 118
private const val CHAR_LCUB = '{'        // ASCII 123
private const val CHAR_TILDE = '~'       // ASCII 126
private const val CHAR_DEL = '\u007F'    // ASCII 127

open class SH : Shell {
  private val specialEscape by lazy { charArrayOf(CHAR_DOLLAR, CHAR_APOS, CHAR_BSOL) }

  override val name = "sh"

  override fun isFlagSafe(char: Char) = char.isPrintable

  override fun startString(appender: CharacterAppender) = appender.append(CHAR_APOS)

  override fun endString(appender: CharacterAppender) = appender.append(CHAR_APOS)

  override fun isArgumentSafe(char: Char, inQuotes: Boolean) =
    if (inQuotes) {
      char != CHAR_APOS
    } else {
      when (char) {
        // [ CTRL ], ' ', '!', '"', '#', '$'
        in CHAR_NUL..CHAR_DOLLAR -> false
        // '&', '\'', '(', ')', '*'
        in CHAR_AMP..CHAR_AST -> false
        // ','
        CHAR_COMMA -> false
        // ';', '<'
        in CHAR_SEMI..CHAR_LT -> false
        // '>', '?'
        in CHAR_GT..CHAR_QUEST -> false
        // '[', '\\', ']', '^'
        in CHAR_LSQB..CHAR_HAT -> false
        // '`'
        CHAR_GRAVE -> false
        // '{', '|', '}', '~', DEL
        in CHAR_LCUB..CHAR_DEL -> false
        // everything else
        else -> true
      }
    }

  override fun escapeArgumentChar(char: Char, inQuotes: Boolean, appender: CharacterAppender) {
    if (inQuotes) {
      // Swap the quoting to double quotes to include the apos char, then swap
      // back to single quotes.
      //
      // End result is: '"'"'
      if (char == CHAR_APOS) {
        endString(appender)
        appender.append(CHAR_QUOT)
        appender.append(char)
        appender.append(CHAR_QUOT)
        startString(appender)
      } else {
        appender.append(char)
      }
    } else {
      when {
        // Control Characters
        char < CHAR_SPACE -> when (val c = char.code) {
          // NUL
          0 -> {
            appender.append(CHAR_APOS)
            appender.append(CHAR_APOS)
          }
          // SOH, STX, ETX, EOT, ENQ, ACK
          in 1..6 -> octalEscape(c, appender)
          // BEL
          7 -> ctrlEscape(CHAR_LOWER_A, appender)
          // BS
          8 -> ctrlEscape(CHAR_LOWER_B, appender)
          // HT
          9 -> ctrlEscape(CHAR_LOWER_T, appender)
          // LF
          10 -> ctrlEscape(CHAR_LOWER_N, appender)
          // VT
          11 -> ctrlEscape(CHAR_LOWER_V, appender)
          // FF
          12 -> ctrlEscape(CHAR_LOWER_F, appender)
          // CR
          13 -> ctrlEscape(CHAR_LOWER_R, appender)
          // SO, SI, SLE, DC1, DC2, DC3, DC4, NAK, SYN, ETV, CAN, EM, SUB
          in 14..26 -> octalEscape(c, appender)
          // ESC
          27 -> ctrlEscape(CHAR_LOWER_E, appender)
          // FS, GS, RS, US
          else -> octalEscape(c, appender)
        }

        // Big characters
        char > CHAR_DEL -> unicodeEscape(char, appender)

        // ASCII [ CTRL ], ' ', '!', '"', '#', '$'
        char <= CHAR_DOLLAR -> simpleEscape(char, appender)

        // ASCII '&', '\'', '(', ')', '*'
        char in CHAR_AMP..CHAR_AST -> simpleEscape(char, appender)

        // ASCII ','
        char == CHAR_COMMA -> simpleEscape(char, appender)

        // ASCII ';', '<'
        char in CHAR_SEMI..CHAR_LT -> simpleEscape(char, appender)

        // ASCII '>', '?'
        char in CHAR_GT..CHAR_QUEST -> simpleEscape(char, appender)

        // ASCII '[', '\\', ']', '^'
        char in CHAR_LSQB..CHAR_HAT -> simpleEscape(char, appender)

        // ASCII '`'
        char == CHAR_GRAVE -> simpleEscape(char, appender)

        // ASCII '{', '|', '}', '~'
        char in CHAR_LCUB..CHAR_TILDE -> simpleEscape(char, appender)

        // ASCII DEL
        char == CHAR_DEL -> octalEscape(char.code, appender)

        // We were passed a character that doesn't need escaping.
        else -> appender.append(char)
      }
    }
  }

  /**
   * Escapes the given character code in octal escape notation: `\000`.
   *
   * @param code Character code to escape in octal.
   *
   * @param appender Character appender to write the escaped character code to.
   */
  private fun octalEscape(code: Int, appender: CharacterAppender) {
    val buf = charArrayOf(CHAR_DIGIT_ZERO, CHAR_DIGIT_ZERO, CHAR_DIGIT_ZERO, CHAR_APOS)
    var pos = 2
    var tmp = code

    appender.append(specialEscape)

    while (tmp > 7 && pos > 0) {
      buf[pos] = CHAR_DIGIT_ZERO + tmp % 8
      tmp /= 8
      pos--
    }

    buf[pos] = CHAR_DIGIT_ZERO + tmp

    appender.append(buf)
  }

  private fun ctrlEscape(code: Char, appender: CharacterAppender) {
    appender.append(specialEscape)
    appender.append(code)
    appender.append(CHAR_BSOL)
  }

  private fun simpleEscape(char: Char, appender: CharacterAppender) {
    appender.append(CHAR_BSOL)
    appender.append(char)
  }

  private fun unicodeEscape(char: Char, appender: CharacterAppender) {
    val buf = charArrayOf(CHAR_DIGIT_ZERO, CHAR_DIGIT_ZERO, CHAR_DIGIT_ZERO, CHAR_DIGIT_ZERO, CHAR_APOS)
    var pos = 3
    var tmp = char.code

    appender.append(specialEscape)
    appender.append('u')

    while (tmp > 15 && pos > 0) {
      buf[pos] = hex(tmp % 16)
      tmp /= 16
      pos--
    }

    buf[pos] = hex(tmp)

    appender.append(buf)
  }

  private fun hex(code: Int) =
    when {
      code < 10 -> CHAR_DIGIT_ZERO + code
      else      -> CHAR_UPPER_A + code - 10
    }
}
