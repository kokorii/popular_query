public ArrayList<MBR> Search(Node node, MBR queryWindow, ArrayList<MBR> result) {
      int i;
      
      if(node instanceof LeafNode) {
         for(i=0; i<node.rectangles.size(); i++) {
            if(checkIntersection(node.rectangles.get(i), queryWindow, true)) {
               result.add(node.rectangles.get(i));
            }
         }
      }
      
      else {
         for(i=0; i<node.rectangles.size(); i++) { 
            if(checkIntersection(node.rectangles.get(i), queryWindow, false)) {
               result = (((NonLeafNode)node).childRectangles.get(i), queryWindow, result);
            }
         }
      }
      
      return result;
   }
   
   public boolean checkIntersection(MBR e, MBR queryWindow, boolean leafFlag) {
      if(leafFlag) {
         if( (queryWindow.getLowX() <= e.getLowX()) && (e.getHighX() <= queryWindow.getHighX()) 
               && (queryWindow.getLowY() <= e.getLowY()) && (e.getHighY() <= queryWindow.getHighY()) ) {
            return true;
         }
         
         else 
            return false;
      }
      
      else {
         if( ((e.getLowX() <= queryWindow.getLowX()) && (e.getHighX() <= queryWindow.getLowX())) 
               || ((queryWindow.getHighX() <= e.getLowX()) && (queryWindow.getHighX() <= e.getHighX())) ) {
            if(((e.getLowY() <= queryWindow.getLowY()) && (e.getHighY() <= queryWindow.getLowY())) 
                  || ((queryWindow.getHighY() <= e.getLowY()) && (queryWindow.getHighY() <= e.getHighY()))) {
               return false;
            }
            
            else
               return true;
         }
         
         else 
            return true;
      }
   }