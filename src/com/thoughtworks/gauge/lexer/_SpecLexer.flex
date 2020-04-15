/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge.lexer;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.thoughtworks.gauge.language.token.SpecTokenTypes.*;

%%

%{
  public _SpecLexer() {
    this(null);
  }
%}

%public
%class _SpecLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%caseless
%state INSTEP,INARG,INDYNAMIC,INTABLEHEADER,INTABLEBODY,INTABLEBODYROW,INDYNAMICTABLEITEM,INTABLECELL

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]
TableIdentifier = [|]
StepIdentifier = [*]
NonWhiteSpaceAndIdentifierCharacter = [^ \r\n\t\f#*|]
dynamicArgTablePattern = {WhiteSpace}* [<] {tableChar}* [>] {WhiteSpace}*
ScenarioHeading = {WhiteSpace}* "##" {InputCharacter}* {LineTerminator}? | {WhiteSpace}* {InputCharacter}* {LineTerminator} [-]+ {LineTerminator}?
SpecHeading = {WhiteSpace}* "#" {InputCharacter}* {LineTerminator}? | {WhiteSpace}* {InputCharacter}* {LineTerminator} [=]+ {LineTerminator}?
Tags = {WhiteSpace}* tags {WhiteSpace}? ":" {InputCharacter}* {LineTerminator}?
Keyword = {WhiteSpace}* table {WhiteSpace}? ":" {InputCharacter}* {LineTerminator}?
TeardownIdentifier = "_" "_" "_"+ {WhiteSpace}* {LineTerminator}
%%
<YYINITIAL> {
  {ScenarioHeading}                                 {return SCENARIO_HEADING;}
  {SpecHeading}                                     {return SPEC_HEADING;}
  {Tags}                                            {return TAGS;}
  {Keyword}                                         {return KEYWORD;}
  {StepIdentifier}                                  {yybegin(INSTEP);return STEP_IDENTIFIER;}
  {TableIdentifier}                                 {yybegin(INTABLEHEADER);return TABLE_BORDER;}
  {LineTerminator}? {WhiteSpace}* {NonWhiteSpaceAndIdentifierCharacter}+ {WhiteSpace}* ({StepIdentifier} | [#] | [##] | {TableIdentifier}) {InputCharacter}* {LineTerminator}? {return COMMENT;}
  {TeardownIdentifier}                              {return TEARDOWN_IDENTIFIER;}
  [^]                                               {return COMMENT;}
}

<INSTEP> {
  [^<\"\r\n]*                   {yybegin(INSTEP); return STEP;}
  [\"]                          {yybegin(INARG); return ARG_START; }
  [<]                           {yybegin(INDYNAMIC); return DYNAMIC_ARG_START;}
  {LineTerminator}?             {yybegin(YYINITIAL); return STEP;}
}

<INARG> {
  (\\\"|[^\"])*                 {return ARG; }
  [\"]                          {yybegin(INSTEP); return ARG_END;}
}

<INDYNAMIC> {
  (\\<|\\>|[^\>])*              {return DYNAMIC_ARG; }
  [>]                           {yybegin(INSTEP); return DYNAMIC_ARG_END;}
}

<INDYNAMICTABLEITEM> {
  (\\<|\\>|[^\>|]|[\\\|])            {yybegin(INDYNAMICTABLEITEM); return DYNAMIC_ARG; }
  [>]                                {yybegin(INTABLEBODYROW); return DYNAMIC_ARG_END;}
  {TableIdentifier}                  {yybegin(INTABLEBODYROW); return TABLE_BORDER;}
  {LineTerminator}{WhiteSpace}*      {yybegin(INTABLEBODY);return NEW_LINE;}
}

<INTABLEHEADER> {
    (\\\||[^|\r\n])*                        {yybegin(INTABLEHEADER); return TABLE_HEADER;}
    {TableIdentifier}                       {yybegin(INTABLEHEADER); return TABLE_BORDER;}
    {LineTerminator}{WhiteSpace}*           {yybegin(INTABLEBODY);return NEW_LINE;}
}

<INTABLEBODY> {
    {TableIdentifier}                       {yybegin(INTABLEBODYROW); return TABLE_BORDER;}
    [^]                                     {yypushback(1); yybegin(YYINITIAL);}
}

<INTABLEBODYROW> {
    {WhiteSpace}*                          {yybegin(INTABLEBODYROW); return WHITESPACE;}
    (\\\||[^-<|\r\n])                      {yybegin(INTABLECELL); return TABLE_ROW_VALUE;}
    [-]*                                   {yybegin(INTABLEBODYROW); return TABLE_BORDER;}
    [<]                                    {yybegin(INDYNAMICTABLEITEM); return DYNAMIC_ARG_START;}
    {TableIdentifier}                      {yybegin(INTABLEBODYROW); return TABLE_BORDER;}
    {LineTerminator}{WhiteSpace}*          {yybegin(INTABLEBODY); return NEW_LINE;}
}

<INTABLECELL> {
    (\\\||[^|\r\n])*                {yybegin(INTABLECELL); return TABLE_ROW_VALUE;}
    {TableIdentifier}               {yybegin(INTABLEBODYROW); return TABLE_BORDER;}
    {LineTerminator}{WhiteSpace}*   {yybegin(INTABLEBODY); return NEW_LINE;}
}
