package io.foxcapades.lib.cli.builder.shell

import io.foxcapades.lib.cli.builder.util.CharacterAppender
import io.foxcapades.lib.cli.builder.util.isPrintable

private const val CHAR_APOS = '\''

open class SH : Shell {
  override val name = "sh"

  override fun isFlagSafe(char: Char) = char.isPrintable

  override fun startString(appender: CharacterAppender) = appender.append('\'')

  override fun endString(appender: CharacterAppender) = appender.append('\'')

  override fun isArgumentSafe(char: Char, inQuotes: Boolean) =
    if (inQuotes) {
      char != CHAR_APOS
    } else {
      when (char) {
        in '\u0000'..'$' -> false // CTRL, ' ', '!', '"', '#', '$'
        in '&'..'*'      -> false // '&', '\'', '(', ')', '*'
        ','                  -> false
        in ';'..'<'      -> false // ';', '<'
        in '>'..'?'      -> false // '>', '?'
        in '['..'^'      -> false // '[', '\\', ']', '^'
        '`'                  -> false
        in '{'..'\u007F' -> false // '{', '|', '}', '~', DEL
        else                 -> true
      }
    }

  override fun escapeArgumentChar(char: Char, inQuotes: Boolean, appender: CharacterAppender) {
    if (inQuotes) {
      if (char == CHAR_APOS) {
        endString(appender)
        appender.append('"')
        appender.append(char)
        appender.append('"')
        startString(appender)
      } else {
        appender.append(char)
      }
    } else {
      when {
        // Control Characters
        char < ' ' -> when (val c = char.code) {
          0 -> {
            appender.append('\'')
            appender.append('\'')
          }
          in 1..6 -> octalEscape(c, appender)
          7 -> ctrlEscape('a', appender)
          8 -> ctrlEscape('b', appender)
          9 -> ctrlEscape('t', appender)
          10 -> ctrlEscape('n', appender)
          11 -> ctrlEscape('v', appender)
          12 -> ctrlEscape('f', appender)
          13 -> ctrlEscape('r', appender)
          in 14..26 -> octalEscape(c, appender)
          27 -> ctrlEscape('E', appender)
          else -> octalEscape(c, appender)
        }

        // Big characters
        char > '\u007F' -> unicodeEscape(char, appender)

        // ASCII special chars
        char <= '$' -> simpleEscape(char, appender)
        char in '&'..'*' -> simpleEscape(char, appender)
        char == ','          -> simpleEscape(char, appender)
        char in ';'..'<' -> simpleEscape(char, appender)
        char in '>'..'?' -> simpleEscape(char, appender)
        char in '['..'^' -> simpleEscape(char, appender)
        char == '`'          -> simpleEscape(char, appender)
        char in '{'..'~' -> simpleEscape(char, appender)
        char == '\u007f'     -> octalEscape(char.code, appender)

        // We were passed a character that doesn't need escaping.
        else -> appender.append(char)
      }
    }
  }

  private fun octalEscape(code: Int, appender: CharacterAppender) {
    val buf = CharArray(3) { '0' }
    var pos = 2
    var tmp = code

    appender.append('$')
    appender.append('\'')
    appender.append('\\')

    while (tmp > 7 && pos > 0) {
      buf[pos] = '0' + tmp % 8
      tmp /= 8
      pos--
    }

    buf[pos] = '0' + tmp

    for (c in buf)
      appender.append(c)

    appender.append('\'')
  }

  private fun ctrlEscape(code: Char, appender: CharacterAppender) {
    appender.append('$')
    appender.append('\'')
    appender.append('\\')
    appender.append(code)
    appender.append('\'')
  }

  private fun simpleEscape(char: Char, appender: CharacterAppender) {
    appender.append('\\')
    appender.append(char)
  }

  private fun unicodeEscape(char: Char, appender: CharacterAppender) {
    val buf = CharArray(4) { '0' }
    var pos = 3
    var tmp = char.code

    appender.append('$')
    appender.append('\'')
    appender.append('\\')
    appender.append('u')

    while (tmp > 15 && pos > 0) {
      buf[pos] = hex(tmp % 16)
      tmp /= 16
      pos--
    }

    buf[pos] = hex(tmp)

    for (c in buf)
      appender.append(c)

    appender.append('\'')
  }

  private fun hex(code: Int) =
    when {
      code < 10 -> '0' + code
      else      -> 'A' + code - 10
    }
}
