package mcts;
import java.util.*;
public class Node 
{
State state;
Node parent;
List<Node> children;
public void setState(State s)
{state=s;}
public State getState()
{return state;}
public void setParent(Node p)
{parent=p;}
public Node getParent()
{return parent;}
public List<Node> getChildren()
{return children;}
}
