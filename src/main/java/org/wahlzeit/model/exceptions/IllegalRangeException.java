package org.wahlzeit.model.exceptions;

public class IllegalRangeException extends RuntimeException {
  // It would be better to use generics, but since Java uses type erasure
  // with generics this is not possible for exceptions....
  private final Number from;
  private final Number to;
  private final String variable;
  private final Number value;
  private final boolean fromInclusive;
  private final boolean toInclusive;


  public IllegalRangeException(String variable, Number from, Number to, Number value) {
    this(variable, from, to, value, true, true);
  }

  public IllegalRangeException(String variable, Number from, Number to, Number value,
                               boolean fromInclusive, boolean toInclusive) {
    super();
    this.from = from;
    this.to = to;
    this.variable = variable;
    this.value = value;
    this.fromInclusive = fromInclusive;
    this.toInclusive = toInclusive;
  }

  public Number getFrom() {
    return from;
  }

  public Number getTo() {
    return to;
  }

  public String getVariable() {
    return variable;
  }

  public Number getValue() {
    return value;
  }

  @Override
  public String toString() {
    char fromBracket = fromInclusive ? '[' : '(';
    char toBracket = toInclusive ? ']' : ')';

    return String.format("Variable %s was %d, but should be in range %c%d, %d%c.",
        variable, value, fromBracket, from, to, toBracket);
  }
}
