import { AxiosInstance } from "axios"
import { ResponseCoordinatorAllStudentCourseWishlist, ResponsePreApprovalForm, ResponsePreApprovalFormList } from "../../types/responseTypes"


export const getSubmittedPreApprovalsCoord = async (axios: AxiosInstance, coordinatorId: string) => {
    const response = await axios.get<ResponsePreApprovalFormList>(`/preapproval/coordinator/${coordinatorId}`)
    console.log(response)
    return response.data
}

export const getSubmittedWishlistsForCoordinator = async (axios: AxiosInstance, coordinatorId: string) => {
    const response = await axios.get<ResponseCoordinatorAllStudentCourseWishlist>(`/wishlist/fetch/coordinator/${coordinatorId}`)
    console.log(response)
    return response.data
}

export const approveSubmittedPreApprovalCoord = async (axios: AxiosInstance, coordinatorId: string, studentId: string) => {
    const response = await axios.post<ResponsePreApprovalForm>(`preapproval/coordinator/${coordinatorId}/student/${studentId}/approve`)
    return response.data
}

export const rejectSubmittedPreApprovalCord = async (axios: AxiosInstance, coordinatorId: string, studentId: string) => {
    const response = await axios.post<ResponsePreApprovalForm>(`preapproval/coordinator/${coordinatorId}/student/${studentId}/reject`)
    return response.data
}