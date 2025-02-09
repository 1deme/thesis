# Similarity-Based Constraint Solver Modulo Commutativity

This project implements a similarity-based constraint-solving algorithm modulo commutativity, extending classical methods to handle fuzzy relations and approximate reasoning. The solver can process equations even with function symbol mismatches and varying arities. It is implemented in Java.
## Features
- Solves first-order equations with fuzzy relations.
- Supports commutative function symbols.
- Handles function symbol mismatches and arity variations.
## Example Usage

Given the constraint:

```math
f_u(x,f_o(x,b),y,h_o(x,y)) ?= g_u(g_o(a,y),b)  
```
and R
```math
- R(f_u, g_u) = 0.5  
- R(f_o, g_o) = 0.6
- R(h_o, g_o) = 0.3
```
it gives solution
```math
⟨x = g_o(a,y) ∧ y = b; 0.6⟩ ∨ ⟨ x = a ∧ y = b; 0.5⟩
```
## How to Use  

1. Download the project.  
2. Run the server with the following command:  

   ```sh
   mvn clean compile exec:java -Dexec.mainClass="com.example.Main"
3. Open 
   ```sh
    localhost:8080/.
