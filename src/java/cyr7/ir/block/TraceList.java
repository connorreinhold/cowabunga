//package cyr7.ir.block;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import cyr7.ir.nodes.IRStmt;
//
//public class TraceList extends LinkedList<ArrayList<BasicBlock>> {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = -4678380381915982554L;
//
//    public List<IRStmt> flattenToStmt() {
//        List<IRStmt> stmts = new LinkedList<>();
//        this.forEach(lst -> {
//            lst.forEach(block -> {
//                stmts.addAll(block.stmts);
//            });
//        });
//        return stmts;
//    }
//
//}