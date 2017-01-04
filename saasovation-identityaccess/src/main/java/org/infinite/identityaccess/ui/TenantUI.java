//package org.infinite.identityaccess.ui;
//
//import com.abigdreamer.infinity.collection.DataTable;
//import com.abigdreamer.infinity.persistence.jdbc.QueryBuilder;
//import org.infinite.framework.annotation.Alias;
//import org.infinite.framework.annotation.Priv;
//import org.infinite.identityaccess.application.ApplicationServiceRegistry;
//import org.infinite.identityaccess.application.command.ProvisionTenantCommand;
//import org.infinite.jaf.core.UIFacade;
//import org.infinite.jaf.ui.control.TreeAction;
//
///**   
// * 
// * @author Darkness
// * @date 2014-11-28 下午12:55:30 
// * @version V1.0   
// */
//@Alias(IdentityAccessConstants.Tenant)
//public class TenantUI extends UIFacade {
//
//	@Priv(IdentityAccessConstants.IdentityAccess + "." + IdentityAccessConstants.Tenant)
//	public void bindTree(TreeAction ta) {
//		QueryBuilder q = new QueryBuilder().select("name", "tenant_id_id").from("tbl_tenant").orderby("name");
//		DataTable dt = q.fetch();
//		dt.insertColumn("TreeLevel", "1");
//		ta.setRootText("租赁");
//		ta.setIdentifierColumnName("tenant_id_id");
//		ta.setBranchIcon("icons/icon025a1.png");
//		ta.setLeafIcon("icons/icon025a1.png");
//		ta.bindData(dt);
//	}
//	
//	@Priv(IdentityAccessConstants.IdentityAccess + "." + IdentityAccessConstants.Tenant)
//	public void provisionTenant(ProvisionTenantCommand command) {
//		ApplicationServiceRegistry.identityApplicationService().provisionTenant(command);
//	}
//	
//}
