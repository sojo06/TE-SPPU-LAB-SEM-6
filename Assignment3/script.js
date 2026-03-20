/*
  Simple Calculator
  - Operations: +, -, *, /, square
  - Validation: numeric input only; prevents invalid states
  - Uses prompt() + alert() for invalid/missing values
*/

(function () {
  "use strict";

  const displayEl = document.getElementById("display");
  const statusEl = document.getElementById("status");
  const memoryEl = document.getElementById("memory");
  const keysEl = document.querySelector(".keys");

  /** @type {number|null} */
  let leftOperand = null;
  /** @type {'+'|'-'|'*'|'/'|null} */
  let pendingOp = null;
  let isEnteringNewNumber = true;

  function setStatus(message) {
    statusEl.textContent = message;
  }

  function setMemory(text) {
    memoryEl.textContent = text;
  }

  function getDisplayText() {
    return displayEl.value.trim();
  }

  function setDisplayText(text) {
    displayEl.value = String(text);
  }

  function isValidNumberText(text) {
    // Accept: 12, 12.3, .5, 0.5, -3 (minus is not entered by keys, but may come from prompt)
    return /^-?(?:\d+(?:\.\d*)?|\.\d+)$/.test(text);
  }

  function parseDisplayNumber() {
    const text = getDisplayText();
    if (text === "") return null;
    if (!isValidNumberText(text)) return null;
    const value = Number(text);
    if (!Number.isFinite(value)) return null;
    return value;
  }

  function normalizeNumberForDisplay(value) {
    if (!Number.isFinite(value)) return "";
    // Avoid long floating errors in typical student calculators
    const rounded = Math.round((value + Number.EPSILON) * 1e12) / 1e12;
    return String(rounded);
  }

  function requestNumberViaPrompt(promptText) {
    const input = window.prompt(promptText, "");
    if (input === null) return null;

    const trimmed = input.trim();
    if (!isValidNumberText(trimmed)) {
      window.alert("Invalid value. Please enter a valid number.");
      return requestNumberViaPrompt(promptText);
    }

    const value = Number(trimmed);
    if (!Number.isFinite(value)) {
      window.alert("Invalid value. Please enter a finite number.");
      return requestNumberViaPrompt(promptText);
    }

    return value;
  }

  function clearAll() {
    leftOperand = null;
    pendingOp = null;
    isEnteringNewNumber = true;
    setDisplayText("");
    setMemory("");
    setStatus("Cleared");
  }

  function backspace() {
    const text = getDisplayText();
    if (text.length === 0 || isEnteringNewNumber) {
      setDisplayText("");
      isEnteringNewNumber = false;
      return;
    }

    setDisplayText(text.slice(0, -1));
  }

  function appendDigit(digit) {
    const text = getDisplayText();

    if (isEnteringNewNumber) {
      setDisplayText(digit);
      isEnteringNewNumber = false;
      return;
    }

    // Prevent leading zeros like 0002 (allow 0.x)
    if (text === "0") {
      setDisplayText(digit);
      return;
    }

    setDisplayText(text + digit);
  }

  function appendDot() {
    const text = getDisplayText();

    if (isEnteringNewNumber) {
      setDisplayText("0.");
      isEnteringNewNumber = false;
      return;
    }

    if (text.includes(".")) {
      window.alert("Only one decimal point is allowed.");
      return;
    }

    if (text === "") {
      setDisplayText("0.");
      return;
    }

    setDisplayText(text + ".");
  }

  function ensureCurrentNumberOrPrompt(reason) {
    const current = parseDisplayNumber();
    if (current !== null) return current;

    window.alert("Invalid or missing number: " + reason);
    const value = requestNumberViaPrompt("Enter a valid number:");
    if (value === null) {
      setStatus("Cancelled");
      return null;
    }

    setDisplayText(normalizeNumberForDisplay(value));
    isEnteringNewNumber = false;
    return value;
  }

  function applyOperation(a, op, b) {
    switch (op) {
      case "+":
        return a + b;
      case "-":
        return a - b;
      case "*":
        return a * b;
      case "/":
        if (b === 0) return null;
        return a / b;
      default:
        return null;
    }
  }

  function chooseOperator(op) {
    const current = ensureCurrentNumberOrPrompt("selecting an operator");
    if (current === null) return;

    if (leftOperand === null) {
      leftOperand = current;
      pendingOp = op;
      isEnteringNewNumber = true;
      setMemory(`${normalizeNumberForDisplay(leftOperand)} ${op}`);
      setStatus("Operator selected");
      return;
    }

    if (pendingOp === null) {
      pendingOp = op;
      isEnteringNewNumber = true;
      setMemory(`${normalizeNumberForDisplay(leftOperand)} ${op}`);
      setStatus("Operator selected");
      return;
    }

    // Chain operations: compute previous op with current, then set new op
    const result = applyOperation(leftOperand, pendingOp, current);
    if (result === null) {
      window.alert("Math error (possible division by zero). Operation cancelled.");
      clearAll();
      return;
    }

    leftOperand = result;
    pendingOp = op;
    setDisplayText(normalizeNumberForDisplay(result));
    isEnteringNewNumber = true;
    setMemory(`${normalizeNumberForDisplay(leftOperand)} ${op}`);
    setStatus("Chained");
  }

  function equals() {
    if (pendingOp === null || leftOperand === null) {
      // Nothing to compute: validate current number
      const current = ensureCurrentNumberOrPrompt("pressing equals");
      if (current === null) return;
      setDisplayText(normalizeNumberForDisplay(current));
      setStatus("Ready");
      return;
    }

    const rightOperand = ensureCurrentNumberOrPrompt("computing result");
    if (rightOperand === null) return;

    const result = applyOperation(leftOperand, pendingOp, rightOperand);
    if (result === null) {
      window.alert("Cannot divide by zero. Please try again.");
      const newRight = requestNumberViaPrompt("Enter a non-zero divisor:");
      if (newRight === null) {
        clearAll();
        return;
      }
      if (newRight === 0) {
        window.alert("Still zero. Clearing calculator.");
        clearAll();
        return;
      }

      const retry = applyOperation(leftOperand, pendingOp, newRight);
      if (retry === null) {
        clearAll();
        return;
      }

      setDisplayText(normalizeNumberForDisplay(retry));
      leftOperand = null;
      pendingOp = null;
      isEnteringNewNumber = true;
      setMemory("");
      setStatus("Done");
      return;
    }

    setDisplayText(normalizeNumberForDisplay(result));
    leftOperand = null;
    pendingOp = null;
    isEnteringNewNumber = true;
    setMemory("");
    setStatus("Done");
  }

  function square() {
    let value = parseDisplayNumber();

    if (value === null) {
      window.alert("Square needs a valid number.");
      value = requestNumberViaPrompt("Enter a number to square:");
      if (value === null) {
        setStatus("Cancelled");
        return;
      }
    }

    const result = value * value;
    if (!Number.isFinite(result)) {
      window.alert("Result is too large.");
      clearAll();
      return;
    }

    setDisplayText(normalizeNumberForDisplay(result));
    isEnteringNewNumber = true;
    setStatus("Squared");
  }

  function onKeyClick(target) {
    const digit = target.getAttribute("data-digit");
    const op = target.getAttribute("data-op");
    const action = target.getAttribute("data-action");

    if (digit !== null) {
      appendDigit(digit);
      setStatus("Entering");
      return;
    }

    if (op !== null) {
      chooseOperator(/** @type {'+'|'-'|'*'|'/'} */ (op));
      return;
    }

    switch (action) {
      case "clear":
        clearAll();
        return;
      case "back":
        backspace();
        setStatus("Editing");
        return;
      case "dot":
        appendDot();
        setStatus("Entering");
        return;
      case "equals":
        equals();
        return;
      case "square":
        square();
        return;
      default:
        return;
    }
  }

  // Click handling
  keysEl.addEventListener("click", (ev) => {
    const target = ev.target;
    if (!(target instanceof HTMLElement)) return;
    if (!target.classList.contains("key")) return;
    onKeyClick(target);
  });

  // Demonstrate prompt use immediately if display is empty and user wants
  // (kept minimal; does not force the user)
  setStatus("Ready");
})();
