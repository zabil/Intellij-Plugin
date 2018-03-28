/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

// Copyright 2015 ThoughtWorks, Inc.

// This file is part of getgauge/Intellij-plugin.

// getgauge/Intellij-plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// getgauge/Intellij-plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with getgauge/Intellij-plugin.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.thoughtworks.gauge.language.token.SpecTokenTypes.ARG;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.ARG_END;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.ARG_START;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.COMMENT;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.DYNAMIC_ARG;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.DYNAMIC_ARG_END;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.DYNAMIC_ARG_START;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.KEYWORD;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.NEW_LINE;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.SCENARIO_HEADING;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.SPEC_HEADING;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.STEP;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.STEP_IDENTIFIER;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.TABLE_BORDER;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.TABLE_HEADER;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.TABLE_ROW_VALUE;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.TAGS;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.TEARDOWN_IDENTIFIER;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.WHITESPACE;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>_SpecLexer.flex</tt>
 */
public class _SpecLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int INSTEP = 2;
  public static final int INARG = 4;
  public static final int INDYNAMIC = 6;
  public static final int INTABLEHEADER = 8;
  public static final int INTABLEBODY = 10;
  public static final int INTABLEBODYROW = 12;
  public static final int INDYNAMICTABLEITEM = 14;
  public static final int INTABLECELL = 16;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8, 8
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [8, 6, 7]
   * Total runtime size is 1296 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[ZZ_CMAP_Z[ch>>13]|((ch>>7)&0x3f)]<<7)|(ch&0x7f)];
  }

  /* The ZZ_CMAP_Z table has 136 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\207\100");

  /* The ZZ_CMAP_Y table has 128 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\1\2\175\1");

  /* The ZZ_CMAP_A table has 384 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\0\1\24\1\6\6\0\1\5\2\0\1\11\14\0\1\17"+
    "\1\0\1\7\1\12\1\10\2\0\1\14\1\20\2\0\1\22\1\0\1\15\4\0\1\21\6\0\1\16\1\13"+
    "\7\0\1\25\2\0\1\23\1\0\1\14\1\20\2\0\1\22\1\0\1\15\4\0\1\21\6\0\1\16\1\13"+
    "\7\0\1\4\u0102\0\1\16");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\0\1\5\1\0"+
    "\1\6\4\7\1\10\1\11\1\12\2\7\1\1\2\13"+
    "\1\14\1\15\1\2\1\16\1\2\1\3\1\17\1\3"+
    "\1\4\2\20\1\21\1\4\1\22\1\23\1\6\2\24"+
    "\1\5\1\25\1\23\1\6\3\26\1\27\1\26\2\6"+
    "\4\0\1\7\3\0\1\30\1\12\3\0\3\12\1\30"+
    "\2\0\1\30\1\12\2\7\1\0\1\7\2\30\2\12"+
    "\2\30\3\0\2\7\2\0\2\31\2\0\1\32\1\0"+
    "\2\32\1\0\3\33";

  private static int [] zzUnpackAction() {
    int [] result = new int[100];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\26\0\54\0\102\0\130\0\156\0\204\0\232"+
    "\0\260\0\306\0\334\0\362\0\u0108\0\u011e\0\u011e\0\u0134"+
    "\0\u014a\0\u0160\0\u0176\0\u018c\0\u01a2\0\u01a2\0\u01a2\0\u01b8"+
    "\0\u01a2\0\u01ce\0\u01e4\0\u01a2\0\u01fa\0\u0210\0\u0226\0\u023c"+
    "\0\u01a2\0\u0252\0\u01a2\0\u01a2\0\u01a2\0\u0268\0\u027e\0\u0294"+
    "\0\u01a2\0\u02aa\0\u02c0\0\u01a2\0\u0226\0\u023c\0\u01a2\0\u02d6"+
    "\0\u02ec\0\u0302\0\306\0\u0318\0\u032e\0\u0344\0\u035a\0\u0370"+
    "\0\362\0\u0386\0\u039c\0\u03b2\0\u0108\0\u011e\0\u014a\0\u03c8"+
    "\0\u03de\0\u032e\0\u03f4\0\u040a\0\u0420\0\u0436\0\u044c\0\u0462"+
    "\0\u032e\0\u0478\0\u048e\0\u04a4\0\u01a2\0\u04ba\0\u01a2\0\u04d0"+
    "\0\u032e\0\u04e6\0\u04fc\0\u0512\0\u0528\0\u01a2\0\u053e\0\u0554"+
    "\0\u056a\0\u032e\0\u0580\0\u0596\0\u05ac\0\u05c2\0\u05d8\0\u032e"+
    "\0\u05ee\0\u0604\0\u061a\0\u032e";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[100];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\4\12"+
    "\1\21\7\12\1\22\2\12\1\23\1\24\1\25\4\23"+
    "\1\26\14\23\1\27\1\23\24\30\1\31\1\32\10\33"+
    "\1\34\14\33\1\35\1\36\1\37\1\40\1\36\1\41"+
    "\20\36\1\42\4\43\1\44\21\43\1\45\1\46\1\47"+
    "\1\50\1\44\2\45\1\51\1\45\1\52\13\45\1\53"+
    "\1\54\1\55\1\56\5\54\1\57\14\54\1\60\1\61"+
    "\1\46\1\47\1\61\1\44\20\61\1\62\1\63\1\64"+
    "\1\65\1\66\3\67\17\63\1\70\1\0\1\71\1\72"+
    "\3\0\2\70\1\73\1\74\14\70\2\0\1\72\3\0"+
    "\2\70\1\73\1\74\13\70\1\63\1\64\1\65\1\75"+
    "\2\76\1\20\4\63\1\77\12\63\1\76\1\64\1\65"+
    "\23\76\1\100\1\101\1\102\3\100\1\103\17\100\1\63"+
    "\1\64\1\65\1\66\3\67\5\63\1\104\12\63\1\64"+
    "\1\65\1\66\3\67\14\63\1\105\2\63\1\23\2\0"+
    "\4\23\1\0\14\23\1\0\1\23\2\0\1\25\51\0"+
    "\24\30\1\0\1\32\25\30\1\32\10\33\1\0\14\33"+
    "\1\35\25\33\1\35\1\36\2\0\1\36\1\0\20\36"+
    "\1\42\2\0\2\40\25\0\1\40\22\0\1\36\2\0"+
    "\22\36\1\42\2\0\2\47\25\0\1\47\25\0\1\50"+
    "\33\0\1\52\20\0\1\45\30\0\2\54\15\0\1\61"+
    "\2\0\1\61\1\0\20\61\1\62\1\61\2\0\22\61"+
    "\1\62\2\0\1\65\6\0\1\106\1\107\24\0\1\106"+
    "\1\107\13\0\1\76\1\64\1\65\1\66\3\67\17\76"+
    "\1\67\1\110\1\111\23\67\1\70\2\0\1\112\3\113"+
    "\20\70\2\0\1\72\3\0\20\70\1\114\1\115\1\112"+
    "\3\113\2\70\1\73\15\70\1\116\1\117\1\112\3\113"+
    "\3\70\1\74\13\70\1\100\1\101\1\102\23\100\2\0"+
    "\1\102\6\0\1\106\1\107\13\0\1\103\1\120\1\121"+
    "\23\103\1\63\1\64\1\65\1\66\3\67\6\63\1\122"+
    "\2\63\1\123\6\63\1\64\1\65\1\66\3\67\14\63"+
    "\1\124\2\63\1\0\1\114\1\115\6\0\1\106\15\0"+
    "\1\116\1\117\7\0\1\107\15\0\1\111\6\0\1\106"+
    "\1\107\16\0\1\112\3\113\17\0\1\113\1\125\1\126"+
    "\23\113\2\0\1\115\25\0\1\117\25\0\1\121\6\0"+
    "\1\106\1\107\13\0\1\63\1\64\1\65\1\66\3\67"+
    "\7\63\1\127\10\63\1\64\1\65\1\66\3\67\12\63"+
    "\1\130\5\63\1\131\1\132\1\133\3\67\14\63\1\124"+
    "\2\63\2\0\1\126\23\0\1\63\1\64\1\65\1\134"+
    "\3\67\10\63\1\135\7\63\1\64\1\65\1\66\3\67"+
    "\13\63\1\136\3\63\2\0\1\132\6\0\1\106\1\107"+
    "\13\0\1\76\1\131\1\132\1\133\3\67\20\76\1\64"+
    "\1\65\1\66\3\67\10\76\1\135\6\76\1\135\1\137"+
    "\1\140\23\135\1\63\1\64\1\65\1\141\3\67\10\63"+
    "\1\142\6\63\2\0\1\140\6\0\1\106\1\107\13\0"+
    "\1\76\1\64\1\65\1\66\3\67\10\76\1\142\6\76"+
    "\1\142\1\143\1\144\23\142\2\0\1\144\6\0\1\106"+
    "\1\107\13\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1584];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\4\1\1\0\1\1\1\0\14\1\3\11\1\1"+
    "\1\11\2\1\1\11\4\1\1\11\1\1\3\11\3\1"+
    "\1\11\2\1\1\11\2\1\1\11\3\1\4\0\1\1"+
    "\3\0\2\1\3\0\4\1\2\0\4\1\1\0\2\1"+
    "\1\11\1\1\1\11\2\1\3\0\1\1\1\11\2\0"+
    "\2\1\2\0\1\1\1\0\2\1\1\0\3\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[100];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  public _SpecLexer() {
    this(null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public _SpecLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { yybegin(INSTEP); return STEP;
            } 
            // fall through
          case 28: break;
          case 2: 
            { return ARG;
            } 
            // fall through
          case 29: break;
          case 3: 
            { return DYNAMIC_ARG;
            } 
            // fall through
          case 30: break;
          case 4: 
            { yybegin(INTABLEHEADER); return TABLE_HEADER;
            } 
            // fall through
          case 31: break;
          case 5: 
            { yybegin(INTABLEBODYROW); return WHITESPACE;
            } 
            // fall through
          case 32: break;
          case 6: 
            { yybegin(INTABLECELL); return TABLE_ROW_VALUE;
            } 
            // fall through
          case 33: break;
          case 7: 
            { return COMMENT;
            } 
            // fall through
          case 34: break;
          case 8: 
            { yybegin(INTABLEHEADER);return TABLE_BORDER;
            } 
            // fall through
          case 35: break;
          case 9: 
            { yybegin(INSTEP);return STEP_IDENTIFIER;
            } 
            // fall through
          case 36: break;
          case 10: 
            { return SPEC_HEADING;
            } 
            // fall through
          case 37: break;
          case 11: 
            { yybegin(YYINITIAL); return STEP;
            } 
            // fall through
          case 38: break;
          case 12: 
            { yybegin(INDYNAMIC); return DYNAMIC_ARG_START;
            } 
            // fall through
          case 39: break;
          case 13: 
            { yybegin(INARG); return ARG_START;
            } 
            // fall through
          case 40: break;
          case 14: 
            { yybegin(INSTEP); return ARG_END;
            } 
            // fall through
          case 41: break;
          case 15: 
            { yybegin(INSTEP); return DYNAMIC_ARG_END;
            } 
            // fall through
          case 42: break;
          case 16: 
            { yybegin(INTABLEBODY);return NEW_LINE;
            } 
            // fall through
          case 43: break;
          case 17: 
            { yybegin(INTABLEHEADER); return TABLE_BORDER;
            } 
            // fall through
          case 44: break;
          case 18: 
            { yypushback(1); yybegin(YYINITIAL);
            } 
            // fall through
          case 45: break;
          case 19: 
            { yybegin(INTABLEBODYROW); return TABLE_BORDER;
            } 
            // fall through
          case 46: break;
          case 20: 
            { yybegin(INTABLEBODY); return NEW_LINE;
            } 
            // fall through
          case 47: break;
          case 21: 
            { yybegin(INDYNAMICTABLEITEM); return DYNAMIC_ARG_START;
            } 
            // fall through
          case 48: break;
          case 22: 
            { yybegin(INDYNAMICTABLEITEM); return DYNAMIC_ARG;
            } 
            // fall through
          case 49: break;
          case 23: 
            { yybegin(INTABLEBODYROW); return DYNAMIC_ARG_END;
            } 
            // fall through
          case 50: break;
          case 24: 
            { return SCENARIO_HEADING;
            } 
            // fall through
          case 51: break;
          case 25: 
            { return TEARDOWN_IDENTIFIER;
            } 
            // fall through
          case 52: break;
          case 26: 
            { return TAGS;
            } 
            // fall through
          case 53: break;
          case 27: 
            { return KEYWORD;
            } 
            // fall through
          case 54: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
