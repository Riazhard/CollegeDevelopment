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
import java.util.Queue;
import java.util.ArrayDeque;

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
  int size;
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

class BFS{
    static int[] color;
    static int[] distancia;
    static int[] padres;
    
    static int [][] BFS(Grafo g, int inicio){
        Queue<Integer> q = new ArrayDeque();
        color = new int[g.size+1];
        distancia = new int[g.size+1];
        padres = new int[g.size+1];
        
        for(int i = 0;i<g.size+1;i++){
            distancia[i] = Integer.MAX_VALUE;
            padres[i] = -1;
        }
        
        distancia[inicio] = 0;
        color[inicio] = 0;
        q.add(inicio);
        int x;
        while(!q.isEmpty()){
            x = q.poll();
            for(Arco arco: g.adyacentes(x)){
                if(color[arco.nodo_fin] == 0){
                    q.add(arco.nodo_fin);
                    color[arco.nodo_fin] = 1;
                    distancia[arco.nodo_fin] = distancia[x]+1;
                    padres[arco.nodo_fin] = x;
                    //System.out.println(arco.nodo_fin+" "+distancia[arco.nodo_fin]+" "+x);
                }
            }
            color[x] = 2;
        }
        
        int[][] out = new int[2][g.size];
        out[0] = padres;
        out[1] = distancia;
        
        return out;
    }
    
}

class Dijkstra {
    
  static class Par implements Comparable<Par> {
    int nodo;
    int prioridad;
    int transbordos;
    //String

    Par(int nodo, int prioridad) {
      this.nodo = nodo;
      this.prioridad = prioridad;
      //this.transbordos = transbordos;
    }

    @Override
    public int compareTo(Par p) {
      if (prioridad < p.prioridad){
          return -1;
      }else if(prioridad > p.prioridad){
          return 1;
      }//else if()
      return 0;
    }
  }

  static int[][] caminoMasCorto(ArrayList<String> name, Grafo g, int inicio) {
      
    PriorityQueue<Par> pq = new PriorityQueue();
    int [] distancias = new int[inicio+1];
    int [] padres = new int[inicio+1];
    int [] transbordos = new int[inicio+1];

    for (int i = 0; i < inicio+1; i++) {
      distancias[i] = Integer.MAX_VALUE;
      padres[i] = i;
      transbordos[i] = Integer.MAX_VALUE;
    }
	
    distancias[inicio] = 0;
    transbordos[inicio] = 0;
    
    pq.add(new Par(inicio, 0));

    while (!pq.isEmpty()) {
      Par p = pq.poll();
        System.out.println(p.prioridad+" "+distancias[p.nodo]+" "+p.nodo);
      if (p.prioridad > distancias[p.nodo]) {
          //System.out.println("Prioridad de "+p.nodo+" es "+p.prioridad+" vs "+distancias[p.nodo]);
        continue;
      }
      for (Arco arco : g.adyacentes(p.nodo)) {
          System.out.println("De "+p.nodo+" tiene "+ arco.nodo_fin+" "+arco.peso);
        if (distancias[arco.nodo_fin] > distancias[p.nodo] + arco.peso) {
          distancias[arco.nodo_fin] = distancias[p.nodo] + arco.peso;
          transbordos[arco.nodo_fin] = transbordos[p.nodo] + 1;
          padres[arco.nodo_fin] = p.nodo;
            //System.out.println("Padre de "+arco.nodo_fin+" es "+p.nodo);
          System.out.println(arco.nodo_fin + " " + distancias[arco.nodo_fin]);
          pq.add(new Par(arco.nodo_fin, distancias[arco.nodo_fin]));
        }else{
            if(distancias[arco.nodo_fin] == distancias[p.nodo] + arco.peso){
                System.out.println(transbordos[arco.nodo_fin]+" "+transbordos[p.nodo]);
                if(transbordos[arco.nodo_fin] == transbordos[p.nodo]+ 1){
                    System.out.println("entoasdasdasdasdasdadasd");
                    int compare = name.get(padres[arco.nodo_fin]).compareTo(name.get(p.nodo));
                    if(compare > 0){
                        padres[arco.nodo_fin] = p.nodo;
                    }
                }else if(transbordos[arco.nodo_fin] > transbordos[p.nodo]+ 1){
                    System.out.println("hgfhgfhgfhgfhgfhgfhg");
                    padres[arco.nodo_fin] = p.nodo;
                }
                
                System.out.println("Soy igual "+arco.nodo_fin+" "+p.nodo);
                
            }
        }
      }
    }
    
    int[][] out = new int[2][inicio+1];
    out[0] = distancias;
    out[1] = padres;
    return out;
  }
}

public class Ejercicio17 {

    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       StringTokenizer in;
       String comando, estacion1, estacion2;
       
       int tam = Integer.parseInt(br.readLine());
       
       Grafo transmilenio = new Grafo(tam+3);  
       HashMap<String,Integer> estaciones = new HashMap(tam+3);
       ArrayList<String> name = new ArrayList(tam+3);
       
       Integer pos = -1;
       for(int i = 0;i<tam;i++){
           in = new StringTokenizer(br.readLine());
           estacion1 = in.nextToken();
           estacion2 = in.nextToken();
           if(!estaciones.containsKey(estacion1)){
               estaciones.put(estacion1, ++pos);
               name.add(pos, estacion1);
           }
           if(!estaciones.containsKey(estacion2)){
               estaciones.put(estacion2, ++pos);
               name.add(pos, estacion2);
           }
           transmilenio.agregarArco(estaciones.get(estacion1), estaciones.get(estacion2), Integer.parseInt(in.nextToken()));
       }
       
       
       
       estaciones.put("Viejo",++pos);
       name.add(pos, "Viejo");
       transmilenio.agregarArco(estaciones.get("UNIVERSIDAD_NACIONAL"), estaciones.get("Viejo"), 200);
       transmilenio.agregarArco(estaciones.get("CIUDAD_UNIVERSITARIA"), estaciones.get("Viejo"), 350);
       transmilenio.size = pos;
       
        int[][] dijkstra;
        dijkstra = Dijkstra.caminoMasCorto(name, transmilenio, pos);
        int[][] Bfs;
        Bfs = BFS.BFS(transmilenio, pos);
       
       
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
               //System.out.println(estaciones.get(in.nextToken()).intValue());
               String out;
               Integer inicio = estaciones.get(in.nextToken());
               if(inicio == null){
                   System.out.println(":(");
               }else{
                    out = name.get(inicio);
                    if(name.get(dijkstra[1][inicio]).equals(out)){
                        System.out.println(":(");
                    }else{
                         while(true){
                             if(dijkstra[1][inicio] != pos){
                                 inicio = dijkstra[1][inicio];                    
                                 out = out.concat(" "+name.get(inicio));                      
                             }else{
                                 break;
                             }            
                         }
                    System.out.println(out);
                    }
               }
               
           }else if("TIEMPO".equals(comando)){
               Integer out = estaciones.get(in.nextToken());
               if(dijkstra[0][out] == Integer.MAX_VALUE){
                   System.out.println(":(");
               }else{
                   System.out.println(dijkstra[0][out]);
               }
           }else{
               Integer out = estaciones.get(in.nextToken());
               if(out == null){
                   System.out.println(":(");
                   continue;
               }
               if(Bfs[1][out] == Integer.MAX_VALUE){
                   System.out.println(":(");
               }else{
                   System.out.println(Bfs[1][out]-1);
               }
           }
    
       }
       
    }
    
}
