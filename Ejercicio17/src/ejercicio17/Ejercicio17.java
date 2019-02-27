/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio17;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;

class Arco {
  int nodo_fin;
  int peso;
        
  public Arco(int nodo_fin, int peso) {
    this.nodo_fin = nodo_fin;
    this.peso = peso;
  }
}

class Grafo {
  int numero_nodos;
  List<Arco>[] arcos;
        
  public Grafo(int numero_nodos) {
            
    this.numero_nodos = numero_nodos;
    arcos = new ArrayList[numero_nodos];
            
    for (int i = 0; i < numero_nodos; i++) {
      arcos[i] = new ArrayList();
    }
  }
        
  public void agregarArco(int nodo_inicio, int nodo_fin, int peso) {
    arcos[nodo_fin].add(new Arco(nodo_inicio, peso));
  }
        
  public List<Arco> adyacentes(int nodo_inicio) {
    return arcos[nodo_inicio];
  }
}

class Dijkstra {
    
  static class Par implements Comparable<Par> {
    int nodo;
    int prioridad;

    Par(int nodo, int prioridad) {
      this.nodo = nodo;
      this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Par p) {
      if (prioridad < p.prioridad) return -1;
      if (prioridad > p.prioridad) return +1;
      return 0;
    }
  }

  static int[] caminoMasCorto(Grafo g, int inicio) {
      
    PriorityQueue<Par> pq = new PriorityQueue();
    int [] distancias = new int[g.numero_nodos];

    for (int i = 0; i < g.numero_nodos; i++) {
      distancias[i] = Integer.MAX_VALUE;
    }
	
    distancias[inicio] = 0;
    pq.add(new Par(inicio, 0));

    while (!pq.isEmpty()) {
      Par p = pq.poll();

      if (p.prioridad > distancias[p.nodo]) {
        continue;
      }
      for (Arco arco : g.adyacentes(p.nodo)) {
        if (distancias[arco.nodo_fin] > distancias[p.nodo] + arco.peso) {
          distancias[arco.nodo_fin] = distancias[p.nodo] + arco.peso;
          pq.add(new Par(arco.nodo_fin, distancias[arco.nodo_fin]));
        }
      }
    }
    return distancias;
  }
}

public class Ejercicio17 {

    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       StringTokenizer in;
       String comando, estacion1, estacion2;
       
       Grafo transmilenio = new Grafo(100000);  
       HashMap<String,Integer> estaciones = new HashMap(104729);
       
       int tam = Integer.parseInt(br.readLine());
       
       Integer pos = -1;
       for(int i = 0;i<tam;i++){
           in = new StringTokenizer(br.readLine());
           estacion1 = in.nextToken();
           estacion2 = in.nextToken();
           if(!estaciones.containsKey(estacion1)){
               estaciones.put(estacion1, ++pos);
           }
           if(!estaciones.containsKey(estacion2)){
               estaciones.put(estacion2, ++pos);
           }
           transmilenio.agregarArco(estaciones.get(estacion1), estaciones.get(estacion2), Integer.parseInt(in.nextToken()));
       }
       
       estaciones.put("Viejo", ++pos);
       transmilenio.agregarArco(estaciones.get("UNIVERSIDAD_NACIONAL"), estaciones.get("Viejo"), 200);
       transmilenio.agregarArco(estaciones.get("CIUDAD_UNIVERSITARIA"), estaciones.get("Viejo"), 350);
       
       tam = Integer.parseInt(br.readLine());
       
       for(int i = 0;i<tam;i++){
           
           in = new StringTokenizer(br.readLine());
           comando = in.nextToken();
           if('E' == comando.charAt(0)){
               if(estaciones.containsKey(in.nextToken())){
                   System.out.println("SI");
               }else{
                   System.out.println("NO");
               }
           }else if('C' == comando.charAt(0)){
               System.out.println("C");
           }else if("TIEMPO".equals(comando)){
               System.out.println("TIEMPO");
           }else{
               System.out.println("Transbordo");
           }
    
       }
    
        
        
    
       
       
       
    }
    
}
