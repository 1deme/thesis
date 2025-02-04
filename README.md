# Similarity-Based Constraint Solver Modulo Commutativity

This project implements a similarity-based constraint-solving algorithm modulo commutativity, extending classical methods to handle fuzzy relations and approximate reasoning. The solver can process equations even with function symbol mismatches and varying arities. It is implemented in Java and features a web-based interface for accessibility.

## Features
- Solves first-order equations with fuzzy relations.
- Supports commutative function symbols.
- Handles function symbol mismatches and arity variations.
- Provides a web-based user interface for interactive use.
## Example Usage

Given the constraint:

```math
f_u(x,f_o(x,b),y,h_o(x,y)) ?= g_u(g_o(a,y),b)

- R(f_u, g_u) = 0.5  
- R(f_o, g_o) = 0.6
- R(h_o, g_o) = 0.3
```
it gives solution
```math
⟨x = g_o(a,y) ∧ y = b; 0.6⟩ ∨ ⟨ x = a ∧ y = b; 0.5⟩
