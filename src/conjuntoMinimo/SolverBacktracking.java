package conjuntoMinimo;

import java.util.HashSet;
import java.util.Set;

import model.Grafo;

public class SolverBacktracking {
	private static Grafo _grafo;
	private static Set<Integer> _conjuntoDominanteMinimo;
	private  Set<Integer> _marcados;
	
	public SolverBacktracking(Grafo grafo) 
	{
        _grafo = grafo;
        _conjuntoDominanteMinimo = new HashSet<>();
        _marcados = new HashSet<>();
    }
	
	public Set<Integer> resolver() 
	{
		resolverBack(0);
		return _conjuntoDominanteMinimo;
	}
	
	// Resuelve el problema de conjunto dominante de forma recursiva, usando backtracking
	private void resolverBack(int vertice) 
	{
		// Llegamos a una hoja.
		if(vertice == _grafo.cantidadVertices()) 
		{
			if(esConjuntoDominante(_marcados)) 
			{
				if(_marcados.size() < _conjuntoDominanteMinimo.size() || _conjuntoDominanteMinimo.isEmpty()) 
						_conjuntoDominanteMinimo = new HashSet<>(_marcados);		
			}
			return;
		}

		// Si superamos el que tenemos, cortamos. (Backtracking)
		if(!_conjuntoDominanteMinimo.isEmpty() &&_marcados.size() >  _conjuntoDominanteMinimo.size())
			return;	
		
		// Caso recursivo
		_marcados.add(vertice);
		resolverBack(vertice + 1);
	
		_marcados.remove(vertice);
		resolverBack(vertice + 1);
	}

	private Set<Integer> vecinosDeMarcados() 
	{
		Set<Integer> vecinosDeMarcados = new HashSet<>();
	
		for(Integer vertice: _marcados) 
			vecinosDeMarcados.addAll(_grafo.vecinoDelVertice(vertice));
		
		return vecinosDeMarcados;
	}

	// Chequea que sea un conjunto dominante el conjunto que tengo hasta ahora.
	private boolean esConjuntoDominante(Set<Integer> conjunto) 
	{
		Set<Integer> grafoCompleto = new HashSet<>();
		grafoCompleto.addAll(vecinosDeMarcados());
		grafoCompleto.addAll(conjunto);
		
		return grafoCompleto.size() == _grafo.cantidadVertices();
	}
}

